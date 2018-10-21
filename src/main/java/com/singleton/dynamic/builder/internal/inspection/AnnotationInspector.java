package com.singleton.dynamic.builder.internal.inspection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.singleton.dynamic.builder.annotation.Not;
import com.singleton.dynamic.builder.internal.representation.BuilderModelImpl;
import com.singleton.dynamic.builder.internal.representation.BuilderPropertyImpl;
import com.singleton.dynamic.builder.representation.BuilderModel;
import com.singleton.dynamic.builder.representation.BuilderProperty;
import com.singleton.dynamic.builder.validation.NotParameterValidator;

/**
 * <p>
 * Builds a representation of a builder class by using reflection and reading parameter annotations.
 * </p>
 *
 * @author Brandon Callison
 */
public class AnnotationInspector
{
    private static final String BUILD_METHOD_NAME = "build"; //$NON-NLS-1$

    /**
     * Inspect the builder interface and produce a model representing its properties and validation rules.
     * 
     * @param builderClass
     *            The interface of the builder.
     * @return A representation of the properties and validation rules of the builder.
     * @throws NoSuchMethodException
     *             If reflection is unable to read the builder methods.
     * @throws SecurityException
     *             If reflection is unable to read the builder methods.
     */
    public BuilderModel inspectBuilder(Class<?> builderClass) throws NoSuchMethodException, SecurityException
    {
        return new BuilderModelImpl.Builder().builderType(builderClass).resultType(getResultType(builderClass))
                .properties(inspectForProperties(builderClass)).build();
    }

    private static Class<?> getResultType(Class<?> builderClass) throws NoSuchMethodException, SecurityException
    {
        return builderClass.getDeclaredMethod(BUILD_METHOD_NAME).getReturnType();
    }

    private static List<BuilderProperty> inspectForProperties(Class<?> builderClass)
    {
        List<BuilderProperty> properties = new ArrayList<BuilderProperty>();

        for (Method method : builderClass.getDeclaredMethods())
        {
            if (builderClass.equals(method.getReturnType()))
            {
                properties.add(createProperty(method));
            }
        }

        return properties;
    }

    private static BuilderProperty createProperty(Method method)
    {
        return new BuilderPropertyImpl.Builder().name(method.getName()).type(method.getParameterTypes()[0])
                .validators(getValidators(method)).build();
    }

    private static NotParameterValidator[] getValidators(Method method)
    {
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();

        List<NotParameterValidator> validators = new ArrayList<NotParameterValidator>();
        for (Annotation annotation : parameterAnnotations[0])
        {
            if (annotation.annotationType().equals(Not.class))
            {
                validators.addAll(Arrays.asList(((Not) annotation).value()));
            }
        }

        return validators.toArray(new NotParameterValidator[validators.size()]);
    }
}
