package com.singleton.dynamic.builder.producer;

import com.singleton.dynamic.builder.representation.BuilderModel;

/**
 * <p>
 * Produces a class that, when instantiated, performs builder behavior as specified in {@code builderModel}.
 * </p>
 *
 * @author Brandon Callison
 */
public interface Producer
{
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
    Class<?> produceBuilder(BuilderModel builderModel) throws NoSuchFieldException, SecurityException;
}
