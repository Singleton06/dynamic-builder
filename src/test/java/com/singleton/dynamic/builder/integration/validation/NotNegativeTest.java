package com.singleton.dynamic.builder.integration.validation;

import static com.singleton.dynamic.builder.validation.NotParameterValidator.NEGATIVE;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.singleton.dynamic.builder.DynamicBuilderFactory;
import com.singleton.dynamic.builder.annotation.Not;

/**
 * Test class to validate that an argument to a builder method can be declared as not negative and it will be enforced.
 * 
 * @author PK030071
 *
 */
@SuppressWarnings({ "javadoc" })
public class NotNegativeTest
{
    private final DynamicBuilderFactory factory = new DynamicBuilderFactory();

    @Test
    public void testIntValue_NotNegative()
    {
        try
        {
            factory.createBuilderForClass(NotNegativeObjectBuilder.class).intValue(-5);
            fail("Expected IllegalArgumentException but none was thrown when negative validation occurred with negative value");
        }
        catch (IllegalArgumentException e)
        {
            assertThat(e.getMessage(), is("intValue was provided negative, but non negative value is required"));
        }
    }

    private interface NotNegativeObjectBuilder
    {
        NotNegativeObjectBuilder intValue(@Not({ NEGATIVE }) int value);

        NotNegativeObject build();
    }

    private interface NotNegativeObject
    {
        int getIntValue();
    }
}