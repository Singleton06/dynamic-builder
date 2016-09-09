package com.singleton.dynamic.builder.defaults;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

import com.singleton.dynamic.builder.annotation.DefaultValue;

/**
 * Specifies the various types supported by {@link DefaultValue} annotation.
 *
 * @author Prateek Kansal
 */
public enum DefaultValueType
{
    /**
     * Any parameter of type {@link Collection}, {@link Set} or {@link String} will default to empty.
     */
    EMPTY,

    /**
     * Any parameter of type {@link Date} will be default to current date and time.
     */
    CURRENT_DATE_TIME;
}
