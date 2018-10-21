package com.singleton.dynamic.builder;

/**
 * <p>
 * Factory for building dynamic builders and model objects from annotated interfaces.
 * </p>
 *
 * @author Brandon Callison
 */
public interface DynamicBuilderFactory
{
    /**
     * Create an instance of the specified builder {@code clazz}.
     * 
     * @param clazz
     *            The interface that defines the contract of the builder.
     * @return A new instance of the builder.
     */
    public <T> T createBuilderForClass(Class<T> clazz);
}
