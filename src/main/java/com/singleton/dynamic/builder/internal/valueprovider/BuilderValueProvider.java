package com.singleton.dynamic.builder.internal.valueprovider;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.singleton.dynamic.builder.annotation.Immutable;
import com.singleton.dynamic.builder.annotation.SanitizeValue;
import com.singleton.dynamic.builder.internal.common.CollectionUtil;
import com.singleton.dynamic.builder.sanitation.SanitationProvider;
import com.singleton.dynamic.builder.sanitation.SanitizeValueType;

/**
 * Class that will inspect the method that is called and determine what all data massaging and other work needs to be
 * done to produce the end value that will be stored within the builder.
 * 
 * @author Dustin Singleton
 * @author Prateek Kansal
 */
public class BuilderValueProvider
{
    private SanitationProvider sanitationProvider = new SanitationProvider();

    /**
     * <p>
     * Gets the value based on the method provided and the argument to the method. This method expects that the provided
     * {@code method} that is provided only has one argument, which is provided through the {@code argument} value.
     * </p>
     * 
     * <p>
     * This method will go through all of the annotations that are on the provided {@code method} and determine if there
     * are any transformations that need to happen to the provided {@code argument} before it is ultimately stored in
     * the builder class.
     * </p>
     * 
     * @param method
     *            The method to use when determining what transformations to perform on the provided {@code argument}.
     * @param argument
     *            The value that was provided as an argument to the {@code method}.
     * 
     * @return the transformed argument based on the provided {@code method}.
     */
    public Object getValue(Method method, Object argument)
    {
        Annotation defaultValueAnnotation = isMethodContainAnnotationClass(method, SanitizeValue.class);
        Class<?> parameterType = method.getParameterTypes()[0];

        if (argument == null)
        {
            if (defaultValueAnnotation != null)
            {
                SanitizeValueType valueType = ((SanitizeValue) defaultValueAnnotation).value();
                return sanitationProvider.sanitize(parameterType, valueType);
            }
            return null;
        }

        if (defaultValueAnnotation != null && Collection.class.isAssignableFrom(parameterType)
                && !Collection.class.cast(argument).isEmpty())
        {
            SanitizeValueType valueType = ((SanitizeValue) defaultValueAnnotation).value();
            if (isThisIgnoreNullElements(valueType))
            {
                Collection.class.cast(argument).removeAll(Collections.singleton(null));
            }
        }

        for (Annotation singleAnnotation : method.getParameterAnnotations()[0])
        {
            if (singleAnnotation.annotationType().equals(Immutable.class))
            {
                Object returnValue = getImmutableReturnValue(method, argument);
                if (returnValue != null)
                {
                    return returnValue;
                }
            }
        }

        return argument;
    }

    private boolean isThisIgnoreNullElements(SanitizeValueType valueType)
    {
        return SanitizeValueType.IGNORE_NULL_ELEMENTS.equals(valueType);
    }

    private Object getImmutableReturnValue(Method method, Object argument)
    {
        Class<?> parameterType = method.getParameterTypes()[0];
        if (Date.class.equals(parameterType))
        {
            return new Date(((Date) argument).getTime());
        }
        if (Collection.class.equals(parameterType))
        {
            return CollectionUtil.INSTANCE.copyCollection((Collection<?>) argument);
        }
        if (List.class.equals(parameterType))
        {
            return CollectionUtil.INSTANCE.copyList((List<?>) argument);
        }
        if (Set.class.equals(parameterType))
        {
            return CollectionUtil.INSTANCE.copySet((Set<?>) argument);
        }

        return null;
    }

    private Annotation isMethodContainAnnotationClass(Method method, Class<? extends Annotation> annotationType)
    {
        for (Annotation singleAnnotation : method.getParameterAnnotations()[0])
        {
            if (singleAnnotation.annotationType().equals(annotationType))
            {
                return singleAnnotation;
            }
        }
        return null;
    }
}
