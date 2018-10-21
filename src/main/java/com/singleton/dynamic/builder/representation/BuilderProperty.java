package com.singleton.dynamic.builder.representation;

import java.lang.reflect.Type;

import com.singleton.dynamic.builder.validation.NotParameterValidator;

/**
 * <p>
 * An assignable builder property which is reflected in the resulting model object upon invoking {@code build()}.
 * </p>
 *
 * @author Brandon Callison
 */
public interface BuilderProperty
{
    /**
     * @return The name of the property.
     */
    String getName();

    /**
     * @return The type of the property.
     */
    Type getType();

    /**
     * @return Any validators which must be evaluated against the property.
     */
    NotParameterValidator[] getValidators();
}
