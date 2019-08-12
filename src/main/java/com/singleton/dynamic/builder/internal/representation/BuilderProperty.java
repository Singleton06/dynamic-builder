package com.singleton.dynamic.builder.internal.representation;

import java.lang.reflect.Type;
import java.util.Arrays;

import com.singleton.dynamic.builder.validation.NotParameterValidator;

/**
 * <p>
 * Implementation of {@link BuilderProperty}.
 * </p>
 *
 * @author Brandon Callison
 */
public class BuilderProperty
{
    private final String name;
    private final Type type;
    private final NotParameterValidator[] validators;

    private BuilderProperty(Builder builder)
    {
        name = builder.name;
        type = builder.type;
        validators = builder.validators;
    }

    /**
     * @return The name of the property.
     */
    public String getName()
    {
        return name;
    }

    /**
     * @return The type of the property.
     */
    public Type getType()
    {
        return type;
    }

    /**
     * @return Any validators which must be evaluated against the property.
     */
    public NotParameterValidator[] getValidators()
    {
        return validators;
    }

    @Override
    public String toString()
    {
        return "BuilderPropertyImpl [name=" + name + ", type=" + type + ", validators=" + Arrays.toString(validators) //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                + "]"; //$NON-NLS-1$
    }

    /**
     * <p>
     * Builder for {@link BuilderProperty}.
     * </p>
     *
     * @author Brandon Callison
     */
    public static class Builder
    {
        private String name;
        private Type type;
        private NotParameterValidator[] validators;

        /**
         * Specifies the name of the builder property.
         * 
         * @param name
         *            Name of the builder property.
         * @return This builder.
         */
        public Builder name(String name)
        {
            this.name = name;

            return this;
        }

        /**
         * Specifies the {@link Type} of the value this property may hold.
         * 
         * @param type
         *            The {@link Type}, (or {@link Class}) specifying the property's type.
         * @return This builder.
         */
        public Builder type(Type type)
        {
            this.type = type;

            return this;
        }

        /**
         * Specify which validators, if any, must be invoked when setting this property's value.
         * 
         * @param validators
         *            An array of {@link NotParameterValidator}. This may be empty.
         * @return This builder.
         */
        public Builder validators(NotParameterValidator[] validators)
        {
            this.validators = validators;

            return this;
        }

        /**
         * Builds a new instance of {@link BuilderProperty} using the parameters in this builder.
         * 
         * @return New instance of {@link BuilderProperty}.
         */
        public BuilderProperty build()
        {
            return new BuilderProperty(this);
        }
    }
}
