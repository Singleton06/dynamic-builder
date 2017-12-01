package com.singleton.dynamic.builder.validation.integration;

import static com.singleton.dynamic.builder.validation.NotParameterValidator.*;

import org.junit.Test;

import com.singleton.dynamic.builder.annotation.Not;
import com.singleton.dynamic.builder.proxy.ProxyBuilderFactory;

/**
 * Test class to validate that an argument to a builder method can be declared as not null and it
 * will be enforced.
 * 
 * @author Dustin Singleton
 */
@SuppressWarnings("javadoc")
public class NotNullTest
{
    private final ProxyBuilderFactory factory = new ProxyBuilderFactory();

    @Test(expected = IllegalArgumentException.class)
    public void testNotNull_nullValue()
    {
        factory.createBuilderForClass(NotNullObjectBuilder.class).stringValue(null);
    }

    private interface NotNullObjectBuilder
    {

        NotNullObjectBuilder stringValue(@Not({ NULL }) String value);

        NotNullObjectBuiltObject build();
    }

    private interface NotNullObjectBuiltObject
    {
        String getStringValue();
    }
}
