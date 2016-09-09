package com.singleton.dynamic.builder.sanitation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.singleton.dynamic.builder.annotation.SanitizeValue;

/**
 * API to sanitize the builder method inputs.
 *
 * @author Prateek Kansal
 */
public class SanitationProvider
{
    /**
     * Sanitize the specified annotated type to its default value. This will only return value for non-primitive types
     * specified using the {@link SanitizeValue annotation} {@link SanitizeValueType value type}.
     * 
     * @param parameterType
     *            The type of the parameter.
     * @param santizeValueType
     *            The {@link SanitizeValueType type value} of the {@link SanitizeValue annotation}.
     * @return The default value specified for the {@code santizeValueType}.
     */
    public Object sanitize(Class<?> parameterType, SanitizeValueType santizeValueType)
    {
        return getDefaultValueForSpecifiedTypes(parameterType, santizeValueType);
    }

    @SuppressWarnings("rawtypes")
    private Object getDefaultValueForSpecifiedTypes(Class<?> paramType, SanitizeValueType santizeValueType)
    {
        if (String.class.isAssignableFrom(paramType))
        {
            if (SanitizeValueType.EMPTY.equals(santizeValueType))
            {
                return "";
            }
        }

        if (Collection.class.equals(paramType))
        {
            if (SanitizeValueType.EMPTY.equals(santizeValueType))
            {
                return new ArrayList();
            }
        }

        if (Date.class.isAssignableFrom(paramType))
        {
            if (SanitizeValueType.CURRENT_DATE_TIME.equals(santizeValueType))
            {
                return new Date();
            }
        }

        if (Set.class.isAssignableFrom(paramType))
        {
            if (SanitizeValueType.EMPTY.equals(santizeValueType))
            {
                return new HashSet();
            }
        }

        return null;
    }
}