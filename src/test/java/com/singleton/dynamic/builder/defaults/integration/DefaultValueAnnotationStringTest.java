package com.singleton.dynamic.builder.defaults.integration;

import static com.singleton.dynamic.builder.defaults.DefaultValueType.EMPTY;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.singleton.dynamic.builder.annotation.DefaultValue;
import com.singleton.dynamic.builder.proxy.ProxyBuilderFactory;

/**
 * Test to validate that if builder methods of parameter type {@link String} is not called then specified default value
 * is used.
 *
 * @author Prateek Kansal
 */
@SuppressWarnings({ "javadoc", "nls" })
public class DefaultValueAnnotationStringTest
{
    @Test
    public void testDefaultValueAnnotation_forEmptyString()
    {
        assertEquals("", new ProxyBuilderFactory().createBuilderForClass(StringDefaultValueObjectBuilder.class)
                .intValue(1).build().getStringValue());
    }

    private interface StringDefaultValueObjectBuilder
    {
        StringDefaultValueObjectBuilder stringValue(@DefaultValue(EMPTY) String value);

        StringDefaultValueObjectBuilder intValue(int value);

        StringDefaultValueObject build();
    }

    private interface StringDefaultValueObject
    {
        String getStringValue();

        int getIntValue();
    }
}
