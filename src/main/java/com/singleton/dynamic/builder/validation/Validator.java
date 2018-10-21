package com.singleton.dynamic.builder.validation;

import java.lang.reflect.Method;

/**
 * <p>
 * Performs validation on a value.
 * </p>
 *
 * @author Brandon Callison
 */
public interface Validator
{
    /**
     * Perform validation as defined by this Validator.
     * 
     * @param valueToValidate
     *            The value which must be validated. The validator determines what input and what is not.
     * @param method
     *            The method providing the setter for this value.
     * @throws IllegalArgumentException
     *             If the Validator's rules determine that the specified value is not valid.
     */
    void validate(Object valueToValidate, Method method);

    /**
     * Perform validation as defined by this Validator.
     * 
     * @param valueToValidate
     *            The value which must be validated. The validator determines what input and what is not.
     * @param propertyName
     *            The property name associated with this value.
     * @throws IllegalArgumentException
     *             If the Validator's rules determine that the specified value is not valid.
     */
    void validate(Object valueToValidate, String propertyName);
}
