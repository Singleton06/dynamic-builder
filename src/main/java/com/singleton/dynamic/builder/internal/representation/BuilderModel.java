package com.singleton.dynamic.builder.internal.representation;

import java.util.List;

/**
 * <p>
 * Implementation of {@link BuilderModel}.
 * </p>
 *
 * @author Brandon Callison
 */
public class BuilderModel
{
    private Class<?> builderType;
    private Class<?> resultType;
    private List<BuilderProperty> properties;

    private BuilderModel(Builder builder)
    {
        properties = builder.properties;
        resultType = builder.resultType;
        builderType = builder.builderType;
    }

    /**
     * @return The interface that defines the builder contract.
     */
    public Class<?> getBuilderType()
    {
        return builderType;
    }

    /**
     * @return The type of object being built.
     */
    public Class<?> getResultType()
    {
        return resultType;
    }

    /**
     * @return The properties that may be assigned using this builder.
     */
    public List<BuilderProperty> getProperties()
    {
        return properties;
    }

    @Override
    public String toString()
    {
        return "BuilderModelImpl [builderType=" + builderType + ", resultType=" + resultType + ", properties=" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                + properties + "]"; //$NON-NLS-1$
    }

    /**
     * <p>
     * Builder for {@link BuilderModel}.
     * </p>
     *
     * @author Brandon Callison
     */
    public static class Builder
    {
        private Class<?> builderType;
        private Class<?> resultType;
        private List<BuilderProperty> properties;

        /**
         * Specify the interface that defines the builder.
         * 
         * @param builderType
         *            Interface that defines the builder. Cannot be null.
         * @return This builder.
         */
        public Builder builderType(Class<?> builderType)
        {
            this.builderType = builderType;
            return this;
        }

        /**
         * Specify the result type (the model object type) from the build method.
         * 
         * @param resultType
         *            The model object interface.
         * @return This builder.
         */
        public Builder resultType(Class<?> resultType)
        {
            this.resultType = resultType;

            return this;
        }

        /**
         * Specify the properties which can be set with this builder.
         * 
         * @param properties
         *            List of properties on the builder.
         * @return This builder.
         */
        public Builder properties(List<BuilderProperty> properties)
        {
            this.properties = properties;

            return this;
        }

        /**
         * Build a new instance of {@link BuilderModel} using the properties in this builder.
         * 
         * @return New instance of {@link BuilderModel}.
         */
        public BuilderModel build()
        {
            return new BuilderModel(this);
        }
    }

}