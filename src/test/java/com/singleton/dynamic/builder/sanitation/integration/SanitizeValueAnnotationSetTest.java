package com.singleton.dynamic.builder.sanitation.integration;

import static com.singleton.dynamic.builder.sanitation.SanitizeValueType.EMPTY;
import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.singleton.dynamic.builder.annotation.SanitizeValue;
import com.singleton.dynamic.builder.proxy.ProxyBuilderFactory;

/**
 * Test to validate the sanitize annotation for the builder method parameter of type {@link Set}.
 *
 * @author Prateek Kansal
 */
@SuppressWarnings("javadoc")
public class SanitizeValueAnnotationSetTest
{
    @Test
    public void testSanitizeSet_EmptyValue()
    {
        assertEquals(new HashSet<Object>(), new ProxyBuilderFactory()
                .createBuilderForClass(SanitizeSetValueObjectBuilder.class).setValue(null).build().getSetValue());
    }

    private interface SanitizeSetValueObjectBuilder
    {
        SanitizeSetValueObjectBuilder setValue(@SanitizeValue(EMPTY) Set<?> value);

        SanitizeSetValueObject build();
    }

    private interface SanitizeSetValueObject
    {
        Set<?> getSetValue();
    }
}
