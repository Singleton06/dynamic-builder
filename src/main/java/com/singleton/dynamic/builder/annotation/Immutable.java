package com.singleton.dynamic.builder.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Annotation to declare that the provided value should be treated as immutable in situations where it might not
 * necessarily be an immutable class. Good examples of this would be Date objects. This means that when the value is
 * provided to a builder, the Date value is copied instead of storing a reference to the same Date object. This ensures
 * that any modifications to the date object provided to the builder will not be reflected within the actual built
 * object.
 * </p>
 * <p>
 * The following are the default supported data types:
 * <ul>
 * <li>{@link List}</li>
 * <li>{@link Collection}</li>
 * <li>{@link Map}</li>
 * <li>{@link Date}</li>
 * </ul>
 * </p>
 * 
 * @author Dustin Singleton
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.PARAMETER })
public @interface Immutable
{
}
