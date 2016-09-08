package com.singleton.dynamic.builder.internal.valueprovider;

import java.lang.annotation.Annotation;
import java.util.Date;

import com.singleton.dynamic.builder.annotation.Immutable;
import com.singleton.dynamic.builder.internal.proxy.ProxiedValue;

/**
 * Class that will inspect the method that is called and determine what all data massaging and other work needs to be
 * done to produce the end value that will be returned for the built object.
 * 
 * @author Dustin Singleton
 */
public class BuiltObjectValueProvider
{
    /**
     * <p>
     * Gets the value based on the {@code proxiedValue}.
     * </p>
     * 
     * <p>
     * This method will go through all of the annotations that are on the provided
     * {@link ProxiedValue#getBuilderMethod()} and determine if there are any transformations that need to happen to the
     * {@link ProxiedValue#getValue()} before it is ultimately returned from the proxied built object.
     * </p>
     * 
     * @param proxiedValue
     *            The value that is currently being looked up on the built object.
     * 
     * @return the transformed argument based on the provided {@code method}.
     */
    public Object getValue(ProxiedValue proxiedValue)
    {
        if (proxiedValue.getValue() == null)
        {
            return null;
        }

        // Assume that there is only one parameter
        Annotation[] builderMethodAnnotations = proxiedValue.getBuilderMethod().getParameterAnnotations()[0];
        for (Annotation singleAnnotation : builderMethodAnnotations)
        {
            if (singleAnnotation.annotationType().equals(Immutable.class))
            {
                Object valueToReturn = proxiedValue.getValue();
                if (Date.class.equals(valueToReturn.getClass()))
                {
                    return new Date(((Date) valueToReturn).getTime());
                }
            }
        }

        return proxiedValue.getValue();
    }
}
