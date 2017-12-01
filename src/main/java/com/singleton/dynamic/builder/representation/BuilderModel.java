package com.singleton.dynamic.builder.representation;

import java.util.List;

/**
 * <p>
 * Representatin of a builder.
 * </p>
 *
 * @author Brandon Callison
 */
public interface BuilderModel
{
    /**
     * @return The interface that defines the builder contract.
     */
    Class<?> getBuilderType();

    /**
     * @return The type of object being built.
     */
    Class<?> getResultType();

    /**
     * @return The properties that may be assigned using this builder.
     */
    List<BuilderProperty> getProperties();
}
