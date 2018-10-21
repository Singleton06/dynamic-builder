package com.singleton.dynamic.builder.validation.integration;

import static com.singleton.dynamic.builder.validation.NotParameterValidator.NEGATIVE;
import static com.singleton.dynamic.builder.validation.NotParameterValidator.ZERO;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.singleton.dynamic.builder.annotation.Not;
import com.singleton.dynamic.builder.proxy.ProxyBuilderFactory;

/**
 * Test class to validate that an argument to a builder method can be declared as positive only and it will be enforced.
 * 
 * @author Prateek Kansal
 *
 */
@SuppressWarnings({ "javadoc", "nls" })
public class MustBePositiveTest
{
    private final ProxyBuilderFactory factory = new ProxyBuilderFactory();

    @Test
    public void testIntValue_NotNegative()
    {
        try
        {
            factory.createBuilderForClass(MustBePositiveObjectBuilder.class).intValue(-5);
            fail("Expected IllegalArgumentException but none was thrown when negative validation occurred with negative value");
        }
        catch (IllegalArgumentException e)
        {
            assertThat(e.getMessage(), is("intValue was provided negative, but non negative value is required"));
        }
    }

    @Test
    public void testIntValue_NotZero()
    {
        try
        {
            factory.createBuilderForClass(MustBePositiveObjectBuilder.class).intValue(0);
            fail("Expected IllegalArgumentException but none was thrown when negative validation occurred with negative value");
        }
        catch (IllegalArgumentException e)
        {
            assertThat(e.getMessage(), is("intValue was provided zero, but non zero value is required"));
        }
    }

    private interface MustBePositiveObjectBuilder
    {
        MustBePositiveObjectBuilder intValue(@Not({ NEGATIVE, ZERO }) int value);

        MustBePositiveObject build();
    }

    private interface MustBePositiveObject
    {
        int getIntValue();
    }
}
