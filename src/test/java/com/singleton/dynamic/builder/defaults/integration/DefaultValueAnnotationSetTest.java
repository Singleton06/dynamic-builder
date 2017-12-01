package com.singleton.dynamic.builder.defaults.integration;

import static com.singleton.dynamic.builder.defaults.DefaultValueType.EMPTY;
import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.singleton.dynamic.builder.annotation.DefaultValue;
import com.singleton.dynamic.builder.proxy.ProxyBuilderFactory;

/**
 * Test to validate that if builder methods of parameter type {@link Set} is not called then specified default value is
 * used.
 *
 * @author Prateek Kansal
 */
@SuppressWarnings("javadoc")
public class DefaultValueAnnotationSetTest
{
    @Test
    public void testDefaultValueAnnotation_forEmptyset()
    {
        assertEquals(new HashSet<Object>(), new ProxyBuilderFactory()
                .createBuilderForClass(DefaultValueSetObjectBuilder.class).intValue(1).build().getSetValue());
    }

    private interface DefaultValueSetObjectBuilder
    {
        DefaultValueSetObjectBuilder setValue(@DefaultValue(EMPTY) Set<?> value);

        DefaultValueSetObjectBuilder intValue(int value);

        DefaultValueSetObject build();
    }

    private interface DefaultValueSetObject
    {
        Set<?> getSetValue();

        int getIntValue();
    }
}
