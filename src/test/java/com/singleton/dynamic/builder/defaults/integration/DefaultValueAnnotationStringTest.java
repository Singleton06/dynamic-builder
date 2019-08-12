package com.singleton.dynamic.builder.defaults.integration;

import static com.singleton.dynamic.builder.defaults.DefaultValueType.EMPTY;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.singleton.dynamic.builder.DynamicBuilderFactory;
import com.singleton.dynamic.builder.annotation.DefaultValue;

/**
 * Test to validate that if builder methods of parameter type {@link String} is
 * not called then specified default value is used.
 *
 * @author Prateek Kansal
 */
@SuppressWarnings({ "javadoc", "nls" })
public class DefaultValueAnnotationStringTest {
    @Test
    public void testDefaultValueAnnotation_forEmptyString() {
        assertEquals("", DynamicBuilderFactory.getInstance()
                .createBuilderForClass(StringDefaultValueObjectBuilder.class).intValue(1).build().getStringValue());
    }

    public interface StringDefaultValueObjectBuilder {
        StringDefaultValueObjectBuilder stringValue(@DefaultValue(EMPTY) String value);

        StringDefaultValueObjectBuilder intValue(int value);

        StringDefaultValueObject build();
    }

    public interface StringDefaultValueObject {
        String getStringValue();

        int getIntValue();
    }
}
