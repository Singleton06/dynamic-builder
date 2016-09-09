package com.singleton.dynamic.builder.sanitation;

import java.util.Collection;
import java.util.Set;

import com.singleton.dynamic.builder.annotation.SanitizeValue;

/**
 * Value types to specify for {@link SanitizeValue sanitize} annotation.
 *
 * @author Prateek Kansal
 */
public enum SanitizeValueType
{
    /**
     * Value type to sanitize the input parameter of type {@link Collection}, {@link Set} or {@link String} to empty
     * value, if {@code null} value is provided.
     */
    EMPTY,

    /**
     * Value type to sanitize the input date parameter to current date time, if {@code null} value is provided.
     */
    CURRENT_DATE_TIME,

    /**
     * Value type to sanitize the input collection parameter to ignore the {@code null} elements of the collection.
     */
    IGNORE_NULL_ELEMENTS;
}
