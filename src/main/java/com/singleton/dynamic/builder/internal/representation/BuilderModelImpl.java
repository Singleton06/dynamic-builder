package com.singleton.dynamic.builder.internal.representation;

import java.util.List;

import com.singleton.dynamic.builder.representation.BuilderModel;
import com.singleton.dynamic.builder.representation.BuilderProperty;

/**
 * <p>
 * Implementation of {@link BuilderModel}.
 * </p>
 *
 * @author Brandon Callison
 */
public class BuilderModelImpl implements BuilderModel
{
    private Class<?> builderType;
    private Class<?> resultType;
    private List<BuilderProperty> properties;

    private BuilderModelImpl(Builder builder)
    {
        properties = builder.properties;
        resultType = builder.resultType;
        builderType = builder.builderType;
    }

    @Override
    public Class<?> getBuilderType()
    {
        return builderType;
    }

    @Override
    public Class<?> getResultType()
    {
        return resultType;
    }

    @Override
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
     * Builder for {@link BuilderModelImpl}.
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
         * Build a new instance of {@link BuilderModelImpl} using the properties in this builder.
         * 
         * @return New instance of {@link BuilderModelImpl}.
         */
        public BuilderModelImpl build()
        {
            return new BuilderModelImpl(this);
        }
    }

}