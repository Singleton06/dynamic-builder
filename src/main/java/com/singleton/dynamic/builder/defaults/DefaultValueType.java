package com.singleton.dynamic.builder.defaults;

import java.util.Collection;
import java.util.Date;

import com.singleton.dynamic.builder.annotation.DefaultValue;

/**
 * Specifies the various types supported by {@link DefaultValue} annotation.
 *
 * @author Prateek Kansal
 */
public enum DefaultValueType
{
    /**
     * Any parameter of type {@link Collection} or its sub-classes type will be default to empty.
     */
    EMPTY,

    /**
     * Any parameter of type {@link Date} will be default to current date and time.
     */
    CURRENT_DATE_TIME;
}
