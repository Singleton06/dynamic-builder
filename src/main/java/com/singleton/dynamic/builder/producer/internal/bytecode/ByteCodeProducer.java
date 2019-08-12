package com.singleton.dynamic.builder.producer.internal.bytecode;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import com.singleton.dynamic.builder.internal.representation.BuilderModel;
import com.singleton.dynamic.builder.internal.representation.BuilderProperty;
import com.singleton.dynamic.builder.validation.NotParameterValidator;
import com.singleton.dynamic.builder.validation.Validator;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.enumeration.EnumerationDescription;
import net.bytebuddy.description.field.FieldDescription.InDefinedShape;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType.Builder;
import net.bytebuddy.dynamic.scaffold.InstrumentedType;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.Implementation.Context;
import net.bytebuddy.implementation.Implementation.Target;
import net.bytebuddy.implementation.InvocationHandlerAdapter;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.implementation.bytecode.constant.TextConstant;
import net.bytebuddy.implementation.bytecode.member.FieldAccess;
import net.bytebuddy.implementation.bytecode.member.MethodInvocation;
import net.bytebuddy.implementation.bytecode.member.MethodReturn;
import net.bytebuddy.implementation.bytecode.member.MethodVariableAccess;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.matcher.ElementMatchers;

/**
 * <p>
 * Produces builder and model objects using byte code generation.
 * </p>
 *
 * @author Brandon Callison
 */
public class ByteCodeProducer
{
    private static final String BUILD_METHOD_NAME = "build"; //$NON-NLS-1$

    private BuildInvocationHandler buildInvocationHandler;

    /**
     * Produces a class that, when instantiated, performs builder behavior as specified in {@code builderModel}.
     * 
     * @param builderModel
     *            The representation of the builder to produce.
     * @return Class that instantiates the builder.
     * @throws NoSuchFieldException
     *             If reflection is unable to read the builder methods.
     * @throws SecurityException
     *             If reflection is unable to read the builder methods.
     */
    public Class<?> produceBuilder(BuilderModel builderModel) throws NoSuchFieldException, SecurityException
    {
        if (buildInvocationHandler == null)
        {
            buildInvocationHandler = new BuildInvocationHandler(builderModel);
        }

        Builder<?> builder = new ByteBuddy().subclass(builderModel.getBuilderType());

        for (BuilderProperty property : builderModel.getProperties())
        {
            builder = builder.defineField(property.getName(), property.getType(), Modifier.PUBLIC)
                    .defineMethod(property.getName(), builderModel.getBuilderType(), Modifier.PUBLIC)
                    .withParameter(property.getType()).intercept(new BuilderMethodImplementation(property));
        }

        builder = builder.defineMethod(BUILD_METHOD_NAME, builderModel.getResultType(), Modifier.PUBLIC)
                .intercept(InvocationHandlerAdapter.of(buildInvocationHandler));

        return builder.make().load(getClass().getClassLoader()).getLoaded();
    }

    private static class BuilderMethodImplementation implements Implementation
    {
        private final BuilderProperty property;

        public BuilderMethodImplementation(BuilderProperty property)
        {
            this.property = property;
        }

        @Override
        public InstrumentedType prepare(InstrumentedType instrumentedType)
        {
            return instrumentedType;
        }

        @Override
        public ByteCodeAppender appender(final Target instrumentationTarget)
        {
            return new BuilderMethodAppender(instrumentationTarget, property);
        }
    }

    private static class BuilderMethodAppender implements ByteCodeAppender
    {
        private final Target instrumentationTarget;
        private final BuilderProperty property;

        public BuilderMethodAppender(Target instrumentationTarget, BuilderProperty property)
        {
            this.instrumentationTarget = instrumentationTarget;
            this.property = property;
        }

        @Override
        public Size apply(MethodVisitor methodVisitor, Context implementationContext,
                MethodDescription instrumentedMethod)
        {
            InDefinedShape fieldToWrite = instrumentationTarget.getInstrumentedType().getDeclaredFields()
                    .filter(ElementMatchers.named(property.getName())).getOnly();

            List<StackManipulation> stack = new ArrayList<StackManipulation>();

            stack.add(new StackManipulation.Compound(MethodVariableAccess.REFERENCE.loadFrom(0),
                    MethodVariableAccess.REFERENCE.loadFrom(0)));

            for (NotParameterValidator validator : property.getValidators())
            {
                stack.add(performValidation(validator));
            }

            stack.add(new StackManipulation.Compound(loadFrom(1), FieldAccess.forField(fieldToWrite).write(),
                    MethodReturn.REFERENCE));

            StackManipulation.Size size = new StackManipulation.Compound(stack).apply(methodVisitor,
                    implementationContext);

            return new Size(size.getMaximalSize(), instrumentedMethod.getStackSize());
        }

        private StackManipulation performValidation(NotParameterValidator validator)
        {
            try
            {
                EnumerationDescription enumerationDescription = new EnumerationDescription.Latent(
                        new TypeDescription.ForLoadedType(NotParameterValidator.class), validator.name());
                Method actualMethod = Validator.class.getDeclaredMethod("validate", Object.class, //$NON-NLS-1$
                        String.class);

                MethodDescription.InDefinedShape validateMethod = new MethodDescription.ForLoadedMethod(actualMethod)
                        .asDefined();

                return new StackManipulation.Compound(FieldAccess.forEnumeration(enumerationDescription), loadFrom(1),
                        new TextConstant(property.getName()), MethodInvocation.invoke(validateMethod));
            }
            catch (NoSuchMethodException e)
            {
                throw new RuntimeException(e);
            }
            catch (SecurityException e)
            {
                throw new RuntimeException(e);
            }
        }

        private StackManipulation loadFrom(int index)
        {
            if (long.class.equals(property.getType()))
            {
                return MethodVariableAccess.LONG.loadFrom(index);
            }
            else if (int.class.equals(property.getType()))
            {
                return MethodVariableAccess.INTEGER.loadFrom(index);
            }

            return MethodVariableAccess.REFERENCE.loadFrom(index);
        }
    }
}
