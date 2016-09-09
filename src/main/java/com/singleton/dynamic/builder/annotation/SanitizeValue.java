package com.singleton.dynamic.builder.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.singleton.dynamic.builder.sanitation.SanitizeValueType;

/**
 * Specifies the annotation to sanitize the input parameter value by specifying {@link SanitizeValueType sanitize value
 * type}.
 *
 * @author Prateek Kansal
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface SanitizeValue
{
    /**
     * @return The {@link SanitizeValueType} used to specify the type of sanitize value.
     */
    SanitizeValueType value();
}
