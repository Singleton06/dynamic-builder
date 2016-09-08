package com.singleton.dynamic.builder.integration.validation;

import static com.singleton.dynamic.builder.validation.NotParameterValidator.ZERO;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.singleton.dynamic.builder.DynamicBuilderFactory;
import com.singleton.dynamic.builder.annotation.Not;

/**
 * Test class to validate that an argument to a builder method can be declared as not to have zero value and it will be
 * enforced.
 * 
 * @author PK030071
 *
 */
public class NotZeroTest
{
    private final DynamicBuilderFactory factory = new DynamicBuilderFactory();

    @Test
    public void testIntValue_NotNegative()
    {
        try
        {
            factory.createBuilderForClass(NotZeroObjectBuilder.class).intValue(0);
            fail("Expected IllegalArgumentException but none was thrown when validation occurred with zero value");
        }
        catch (IllegalArgumentException e)
        {
            assertThat(e.getMessage(), is("intValue was provided zero, but non zero value is required"));
        }
    }

    private interface NotZeroObjectBuilder
    {
        NotZeroObjectBuilder intValue(@Not({ ZERO }) int value);

        NotZeroObject build();
    }

    private interface NotZeroObject
    {
        int getIntValue();
    }
}
