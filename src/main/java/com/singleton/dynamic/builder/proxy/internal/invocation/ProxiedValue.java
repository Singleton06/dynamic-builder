package com.singleton.dynamic.builder.proxy.internal.invocation;

import java.lang.reflect.Method;

/**
 * Object that contains a value that has been set as a result of it being proxied.
 * 
 * @author Dustin Singleton
 */
public class ProxiedValue
{
    private final Method builderMethod;
    private final Object value;

    /**
     * Default constructor.
     * 
     * @param builderMethod
     *            The method that was called on the builder interface that was used to set this value.
     * @param value
     *            The value of the parameter that was provided with the {@code method} was called.
     * 
     * @throws IllegalArgumentException
     *             if the {@code builderMethod} provided is {@code null}
     */
    public ProxiedValue(Method builderMethod, Object value)
    {
        if (builderMethod == null)
        {
            throw new IllegalArgumentException("builderMethod cannot be null"); //$NON-NLS-1$
        }

        this.builderMethod = builderMethod;
        this.value = value;
    }

    /**
     * @return the method that was called on the builder.
     */
    public Method getBuilderMethod()
    {
        return builderMethod;
    }

    /**
     * @return the value that was provided to the {@link #getBuilderMethod() builder method}.
     */
    public Object getValue()
    {
        return value;
    }
}
