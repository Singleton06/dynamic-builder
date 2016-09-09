package com.singleton.dynamic.builder.integration.defaults;

import static com.singleton.dynamic.builder.defaults.DefaultValueType.EMPTY;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.singleton.dynamic.builder.DynamicBuilderFactory;
import com.singleton.dynamic.builder.annotation.DefaultValue;

/**
 * Test to validate the builder method annotated with default value for type {@link String}.
 *
 * @author Prateek Kansal
 */
@SuppressWarnings("javadoc")
public class StringDefaultValueAnnotationTest
{
    @Test
    public void testDefaultValueAnnotation_forEmptyString()
    {
        assertEquals("",
                new DynamicBuilderFactory().createBuilderForClass(StringDefaultValueObjectBuilder.class)
                        .stringValue(null).build().getStringValue());
    }

    private interface StringDefaultValueObjectBuilder
    {
        StringDefaultValueObjectBuilder stringValue(@DefaultValue(EMPTY) String value);

        StringDefaultValueObject build();
    }

    private interface StringDefaultValueObject
    {
        String getStringValue();
    }
}
