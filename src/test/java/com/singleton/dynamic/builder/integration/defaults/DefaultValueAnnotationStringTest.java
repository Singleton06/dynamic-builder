package com.singleton.dynamic.builder.integration.defaults;

import static com.singleton.dynamic.builder.defaults.DefaultValueType.EMPTY;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.singleton.dynamic.builder.DynamicBuilderFactory;
import com.singleton.dynamic.builder.annotation.DefaultValue;

/**
 * Test to validate that if builder methods of parameter type {@link String} is not called then specified default value
 * is used.
 *
 * @author Prateek Kansal
 */
@SuppressWarnings("javadoc")
public class DefaultValueAnnotationStringTest
{
    @Test
    public void testDefaultValueAnnotation_forEmptyString()
    {
        assertEquals("",
                new DynamicBuilderFactory().createBuilderForClass(StringDefaultValueObjectBuilder.class)
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
