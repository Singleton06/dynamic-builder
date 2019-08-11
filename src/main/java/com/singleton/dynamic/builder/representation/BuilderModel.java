package com.singleton.dynamic.builder.representation;

import java.util.List;

/**
 * Representatin of a builder.
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
