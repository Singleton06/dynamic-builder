package com.singleton.dynamic.builder.producer.internal.bytecode;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.singleton.dynamic.builder.internal.representation.BuilderModel;
import com.singleton.dynamic.builder.internal.representation.BuilderProperty;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.field.FieldDescription.InDefinedShape;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType.Builder;
import net.bytebuddy.dynamic.scaffold.InstrumentedType;
import net.bytebuddy.dynamic.scaffold.subclass.ConstructorStrategy;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.Implementation.Context;
import net.bytebuddy.implementation.Implementation.Target;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.implementation.bytecode.member.FieldAccess;
import net.bytebuddy.implementation.bytecode.member.MethodInvocation;
import net.bytebuddy.implementation.bytecode.member.MethodReturn;
import net.bytebuddy.implementation.bytecode.member.MethodVariableAccess;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.matcher.ElementMatchers;

/**
 * <p>
 * Invocation handler for building a model object.
 * </p>
 *
 * @author Brandon Callison
 */
public class BuildInvocationHandler implements InvocationHandler
{
    private static final String BOOLEAN_PREFIX = "is"; //$NON-NLS-1$
    private static final String GETTER_PREFIX = "get"; //$NON-NLS-1$

    private BuilderModel model;
    private Class<?> targetClass;

    /**
     * Construct a build model object invocation handler;
     * 
     * @param model
     *            description of the model to build
     */
    public BuildInvocationHandler(BuilderModel model)
    {
        this.model = model;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
    {
        if (targetClass == null)
        {
            targetClass = buildModelClass(model, proxy.getClass(), proxy.getClass().getClassLoader());
        }

        return targetClass.getConstructor(proxy.getClass()).newInstance(proxy);
    }

    private static Class<?> buildModelClass(BuilderModel model, Class<?> actualBuilderType, ClassLoader classLoader)
    {
        Builder<?> builder = new ByteBuddy().subclass(model.getResultType(),
                ConstructorStrategy.Default.DEFAULT_CONSTRUCTOR);

        for (BuilderProperty property : model.getProperties())
        {
            builder = builder.defineField(property.getName(), property.getType(), Modifier.PRIVATE)
                    .defineMethod(determineGetterName(property.getName(), property.getType()), property.getType(),
                            Modifier.PUBLIC)
                    .intercept(new GetterImplementation(property));
        }

        builder = builder.defineConstructor(Visibility.PUBLIC).withParameter(actualBuilderType)
                .intercept(buildConstructor(model, actualBuilderType));

        return builder.make().load(classLoader).getLoaded();
    }

    private static Implementation buildConstructor(final BuilderModel model, final Class<?> builderClass)
    {
        return new Implementation()
        {
            @Override
            public InstrumentedType prepare(InstrumentedType instrumentedType)
            {
                return instrumentedType;
            }

            @Override
            public ByteCodeAppender appender(final Target instrumentationTarget)
            {
                return new ByteCodeAppender()
                {
                    @Override
                    public Size apply(MethodVisitor methodVisitor, Context instrumentationContext,
                            MethodDescription instrumentedMethod)
                    {
                        List<StackManipulation> manipulation = new ArrayList<StackManipulation>();
                        manipulation.add(MethodVariableAccess.REFERENCE.loadFrom(0));
                        manipulation.add(MethodInvocation.invoke(instrumentationTarget.getInstrumentedType()
                                .getDeclaredMethods().filter(ElementMatchers.isConstructor())
                                .filter(ElementMatchers.takesArguments(0)).getOnly()));

                        for (BuilderProperty p : model.getProperties())
                        {
                            manipulation.addAll(copyProperty(instrumentationTarget, model, p));
                        }

                        manipulation.add(MethodReturn.VOID);

                        StackManipulation.Size size = new StackManipulation.Compound(manipulation).apply(methodVisitor,
                                instrumentationContext);
                        return new Size(size.getMaximalSize(), instrumentedMethod.getStackSize());
                    }
                };
            }

            private List<StackManipulation> copyProperty(Target instrumentationTarget, BuilderModel model,
                    BuilderProperty property)
            {
                List<StackManipulation> manipulation = new ArrayList<StackManipulation>();

                InDefinedShape fieldToWrite = instrumentationTarget.getInstrumentedType().getDeclaredFields()
                        .filter(ElementMatchers.named(property.getName())).getOnly();

                InDefinedShape fieldToRead = new TypeDescription.ForLoadedType(builderClass).getDeclaredFields()
                        .filter(ElementMatchers.named(property.getName())).getOnly();

                manipulation.add(MethodVariableAccess.loadThis());
                manipulation.addAll(Arrays.asList(MethodVariableAccess.REFERENCE.loadFrom(1),
                        FieldAccess.forField(fieldToRead).read()));
                manipulation.add(FieldAccess.forField(fieldToWrite).write());

                return manipulation;
            }
        };
    }

    private static class GetterImplementation implements Implementation
    {
        private final BuilderProperty property;

        public GetterImplementation(BuilderProperty property)
        {
            this.property = property;
        }

        @Override
        public InstrumentedType prepare(InstrumentedType instrumentedType)
        {
            return instrumentedType;
        }

        @Override
        public ByteCodeAppender appender(Target instrumentationTarget)
        {
            return new GetterAppender(instrumentationTarget, property);
        }
    }

    private static class GetterAppender implements ByteCodeAppender
    {
        private final Target instrumentationTarget;
        private final BuilderProperty property;

        public GetterAppender(Target instrumentationTarget, BuilderProperty property)
        {
            this.instrumentationTarget = instrumentationTarget;
            this.property = property;
        }

        @Override
        public Size apply(MethodVisitor methodVisitor, Context instrumentationContext,
                MethodDescription instrumentedMethod)
        {
            InDefinedShape fieldToRead = instrumentationTarget.getInstrumentedType().getDeclaredFields()
                    .filter(ElementMatchers.named(property.getName())).getOnly();

            StackManipulation.Size size = new StackManipulation.Compound(MethodVariableAccess.REFERENCE.loadFrom(0),
                    MethodVariableAccess.REFERENCE.loadFrom(0), FieldAccess.forField(fieldToRead).read(),
                    returnType(property)).apply(methodVisitor, instrumentationContext);
            return new Size(size.getMaximalSize(), instrumentedMethod.getStackSize());
        }
    }

    private static StackManipulation returnType(BuilderProperty property)
    {
        if (long.class.equals(property.getType()))
        {
            return MethodReturn.LONG;
        }
        else if (int.class.equals(property.getType()))
        {
            return MethodReturn.INTEGER;
        }

        return MethodReturn.REFERENCE;
    }

    private static String determineGetterName(String propertyName, Type type)
    {
        if (Boolean.class.equals(type) || boolean.class.equals(type))
        {
            return BOOLEAN_PREFIX + camelCase(propertyName);
        }

        return GETTER_PREFIX + camelCase(propertyName);
    }

    private static String camelCase(String str)
    {
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }
}
