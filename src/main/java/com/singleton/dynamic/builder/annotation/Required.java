package com.singleton.dynamic.builder.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that indicates that the method call is required and must be called on builder. If any method with this
 * annotation is not called on the builder then it will throw an {@link IllegalStateException}.
 * 
 * @author Prateek Kansal
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Required
{
    
}
