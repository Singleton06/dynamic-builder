package com.singleton.dynamic.builder.integration.defaults;

import static com.singleton.dynamic.builder.defaults.DefaultValueType.EMPTY;
import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.singleton.dynamic.builder.DynamicBuilderFactory;
import com.singleton.dynamic.builder.annotation.DefaultValue;

/**
 * Test to validate the default value annotation on the builder method parameter of type {@link Set}.
 *
 * @author Prateek Kansal
 */
@SuppressWarnings("javadoc")
public class SetDefaultValueAnnotationTest
{

    @Test
    public void testDefaultValueAnnotation_forEmptyset()
    {
        assertEquals(new HashSet(), new DynamicBuilderFactory()
                .createBuilderForClass(DefaultValueSetObjectBuilder.class).setValue(null).build().getSetValue());
    }

    private interface DefaultValueSetObjectBuilder
    {
        DefaultValueSetObjectBuilder setValue(@DefaultValue(EMPTY) Set<?> value);

        DefaultValueSetObject build();
    }

    private interface DefaultValueSetObject
    {
        Set<?> getSetValue();
    }
}
