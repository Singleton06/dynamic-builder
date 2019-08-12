package com.singleton.dynamic.builder.defaults.integration;

import static com.singleton.dynamic.builder.defaults.DefaultValueType.EMPTY;
import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.singleton.dynamic.builder.DynamicBuilderFactory;
import com.singleton.dynamic.builder.annotation.DefaultValue;

/**
 * Test to validate that if builder methods of parameter type {@link Set} is not
 * called then specified default value is used.
 *
 * @author Prateek Kansal
 */
@SuppressWarnings("javadoc")
public class DefaultValueAnnotationSetTest {
    @Test
    public void testDefaultValueAnnotation_forEmptyset() {
        assertEquals(new HashSet<Object>(), DynamicBuilderFactory.getInstance()
                .createBuilderForClass(DefaultValueSetObjectBuilder.class).intValue(1).build().getSetValue());
    }

    public interface DefaultValueSetObjectBuilder {
        DefaultValueSetObjectBuilder setValue(@DefaultValue(EMPTY) Set<?> value);

        DefaultValueSetObjectBuilder intValue(int value);

        DefaultValueSetObject build();
    }

    public interface DefaultValueSetObject {
        Set<?> getSetValue();

        int getIntValue();
    }
}
