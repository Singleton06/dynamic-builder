package com.singleton.dynamic.builder.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.singleton.dynamic.builder.validation.NotParameterValidator;

/**
 * <p>
 * Annotation that indicates that the parameter is intended to never be specified as {@code null}.
 * </p>
 *
 * @author Dustin Singleton
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Not
{
    /**
     * @return the different validators that should be invoked for the parameter.
     */
    NotParameterValidator[] value();
}
