package com.singleton.dynamic.builder.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.singleton.dynamic.builder.defaults.DefaultValueType;

/**
 * Specifies the default value annotation. The various types for default value will be specified using
 * {@link DefaultValueType} type.
 *
 * @author Prateek Kansal
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface DefaultValue
{
    /**
     * @return The {@link DefaultValueType} used to specify the type of default value.
     */
    DefaultValueType value();
}
