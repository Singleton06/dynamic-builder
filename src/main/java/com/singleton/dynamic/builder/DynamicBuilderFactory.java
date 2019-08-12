package com.singleton.dynamic.builder;

import com.singleton.dynamic.builder.producer.ByteCodeDynamicBuilderFactory;

/**
 * <p>
 * Factory for building dynamic builders and model objects from annotated
 * interfaces.
 * </p>
 *
 * @author Brandon Callison
 * @author Dustin Singleton
 */
public interface DynamicBuilderFactory {
    /**
     * @return a new instance of a {@link DynamicBuilderFactory}. The implementation
     *         is not guaranteed to be specific, just that it will adhere to the
     *         contract provided. This allows for modifications in the future that
     *         will be passive from a code standpoint, but would allow the assembler
     *         to choose the type of dynamic builder implementation chosen.
     */
    public static DynamicBuilderFactory getInstance() {
        return new ByteCodeDynamicBuilderFactory();
    }

    /**
     * Create an instance of the specified builder {@code clazz}.
     * 
     * @param clazz The interface that defines the contract of the builder.
     * @return A new instance of the builder.
     */
    public <T> T createBuilderForClass(Class<T> clazz);
}
