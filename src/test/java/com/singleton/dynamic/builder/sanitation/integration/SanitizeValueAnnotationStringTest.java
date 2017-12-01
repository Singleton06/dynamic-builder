package com.singleton.dynamic.builder.sanitation.integration;

import static com.singleton.dynamic.builder.sanitation.SanitizeValueType.EMPTY;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.singleton.dynamic.builder.annotation.SanitizeValue;
import com.singleton.dynamic.builder.proxy.ProxyBuilderFactory;

/**
 * Test to validate the sanitize annotation for the builder method parameter of type string.
 *
 * @author Prateek Kansal
 */
@SuppressWarnings({ "javadoc", "nls" })
public class SanitizeValueAnnotationStringTest
{
    @Test
    public void testSanitizeString_NullValue()
    {
        assertEquals("", new ProxyBuilderFactory().createBuilderForClass(SanitizeStringValueObjectBuilder.class)
                .stringValue(null).build().getStringValue());
    }

    private interface SanitizeStringValueObjectBuilder
    {
        SanitizeStringValueObjectBuilder stringValue(@SanitizeValue(EMPTY) String value);

        SanitizeStringValueObject build();
    }

    private interface SanitizeStringValueObject
    {
        String getStringValue();
    }
}
