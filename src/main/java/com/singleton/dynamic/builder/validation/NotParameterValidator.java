package com.singleton.dynamic.builder.validation;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

import com.singleton.dynamic.builder.validation.exception.MismatchedValidatorTypeException;

/**
 * <p>
 * Enumeration of supported validation types that can be performed in a "not" situation, such as not {@code null} or not
 * empty.
 * </p>
 *
 * @author Dustin Singleton
 * @author Prateek Kansal
 */
public enum NotParameterValidator implements Validator
{
    /**
     * Validator for ensuring that the specified arguments are not {@code null}.
     */
    NULL
    {
        @Override
        public void validate(Object objectToValidate, Method method)
        {
            if (objectToValidate == null)
            {
                throw new IllegalArgumentException(
                        method.getName() + " was provided null, but non null values are required"); //$NON-NLS-1$
            }
        }

        @Override
        public void validate(Object objectToValidate, String methodName)
        {
            if (objectToValidate == null)
            {
                throw new IllegalArgumentException(methodName + " was provided null, but non null values are required"); //$NON-NLS-1$
            }
        }
    },

    /**
     * Validator for ensuring that the specified arguments are not empty. Supported data types are:
     * <ul>
     * <li>{@link String}</li>
     * </ul>
     */
    EMPTY
    {
        @Override
        public void validate(Object objectToValidate, Method method)
        {
            if (objectToValidate == null)
            {
                return;
            }

            if (String.class.isAssignableFrom(objectToValidate.getClass()))
            {
                if (((String) objectToValidate).isEmpty())
                {
                    throw new IllegalArgumentException(
                            method.getName() + " was provided empty, but non empty values are required"); //$NON-NLS-1$
                }
            }

            if (Collection.class.isAssignableFrom(objectToValidate.getClass()))
            {
                if (((Collection<?>) objectToValidate).isEmpty())
                {
                    throw new IllegalArgumentException(method.getName()
                            + " was provided an empty collection, but a non-empty collection is required"); //$NON-NLS-1$
                }
            }

            if (Map.class.isAssignableFrom(objectToValidate.getClass()))
            {
                if (((Map<?, ?>) objectToValidate).isEmpty())
                {
                    throw new IllegalArgumentException(
                            method.getName() + " was provided an empty map, but a non-empty map is required"); //$NON-NLS-1$
                }
            }
        }

        @Override
        public void validate(Object objectToValidate, String methodName)
        {
            if (objectToValidate == null)
            {
                return;
            }

            if (String.class.isAssignableFrom(objectToValidate.getClass()))
            {
                if (((String) objectToValidate).isEmpty())
                {
                    throw new IllegalArgumentException(
                            methodName + " was provided empty, but non empty values are required"); //$NON-NLS-1$
                }
            }

            if (Collection.class.isAssignableFrom(objectToValidate.getClass()))
            {
                if (((Collection<?>) objectToValidate).isEmpty())
                {
                    throw new IllegalArgumentException(
                            methodName + " was provided an empty collection, but a non-empty collection is required"); //$NON-NLS-1$
                }
            }

            if (Map.class.isAssignableFrom(objectToValidate.getClass()))
            {
                if (((Map<?, ?>) objectToValidate).isEmpty())
                {
                    throw new IllegalArgumentException(
                            methodName + " was provided an empty map, but a non-empty map is required"); //$NON-NLS-1$
                }
            }
        }
    },

    /**
     * Validator for ensuring that the specified arguments are not negative. Supported data types are:
     * <ul>
     * <li>{@link Number}</li>
     * </ul>
     */
    NEGATIVE
    {
        @Override
        public void validate(Object objectToValidate, Method method)
        {
            if (objectToValidate == null)
            {
                return;
            }

            if (Number.class.isAssignableFrom(objectToValidate.getClass()))
            {
                if (((Number) objectToValidate).doubleValue() < 0)
                {
                    throw new IllegalArgumentException(
                            method.getName() + " was provided negative, but non negative value is required"); //$NON-NLS-1$
                }
            }
        }

        @Override
        public void validate(Object objectToValidate, String methodName)
        {
            if (objectToValidate == null)
            {
                return;
            }

            if (Number.class.isAssignableFrom(objectToValidate.getClass()))
            {
                if (((Number) objectToValidate).doubleValue() < 0)
                {
                    throw new IllegalArgumentException(
                            methodName + " was provided negative, but non negative value is required"); //$NON-NLS-1$
                }
            }
        }
    },

    /**
     * Validator for ensuring that the specified arguments are not zero. Supported data types are:
     * <ul>
     * <li>{@link Number}</li>
     * </ul>
     */
    ZERO
    {
        @Override
        public void validate(Object objectToValidate, Method method)
        {
            if (objectToValidate == null)
            {
                return;
            }

            if (Number.class.isAssignableFrom(objectToValidate.getClass()))
            {
                if (((Number) objectToValidate).doubleValue() == 0)
                {
                    throw new IllegalArgumentException(
                            method.getName() + " was provided zero, but non zero value is required"); //$NON-NLS-1$
                }
            }
        }

        @Override
        public void validate(Object objectToValidate, String methodName)
        {
            if (objectToValidate == null)
            {
                return;
            }

            if (Number.class.isAssignableFrom(objectToValidate.getClass()))
            {
                if (((Number) objectToValidate).doubleValue() == 0)
                {
                    throw new IllegalArgumentException(
                            methodName + " was provided zero, but non zero value is required"); //$NON-NLS-1$
                }
            }
        }
    };

    /**
     * Validates the specified {@code objectToValidate}. In situations where the validation does not pass, an
     * {@link IllegalArgumentException} should be thrown. All implementations should make no assumptions about the
     * implementation. If the provided {@code objectToValidate} does not match the supported class types, then an
     * {@link MismatchedValidatorTypeException} should be thrown.
     * 
     * @param objectToValidate
     *            The object to perform the validation on.
     * @param method
     *            The method that the validation was performed on.
     */
    @Override
    public abstract void validate(Object objectToValidate, Method method);
}
