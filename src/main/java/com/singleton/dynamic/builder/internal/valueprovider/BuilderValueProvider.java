package com.singleton.dynamic.builder.internal.valueprovider;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Date;

import com.singleton.dynamic.builder.annotation.Immutable;

/**
 * Class that will inspect the method that is called and determine what all data massaging and other work needs to be
 * done to produce the end value that will be stored within the builder.
 * 
 * @author Dustin Singleton
 */
public class BuilderValueProvider
{
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
        if (argument == null)
        {
            return null;
        }

        for (Annotation singleAnnotation : method.getParameterAnnotations()[0])
        {
            if (singleAnnotation.annotationType().equals(Immutable.class))
            {
                if (Date.class.equals(argument.getClass()))
                {
                    return new Date(((Date) argument).getTime());
                }
            }
        }

        return argument;
    }
}
