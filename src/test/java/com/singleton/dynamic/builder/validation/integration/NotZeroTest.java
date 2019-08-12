package com.singleton.dynamic.builder.validation.integration;

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
 * @author Prateek Kansal
 *
 */
@SuppressWarnings({ "javadoc", "nls" })
public class NotZeroTest
{
    private final DynamicBuilderFactory factory = DynamicBuilderFactory.getInstance();

    @Test
    public void testNotZero_ValueZero()
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

    @Test
    public void testNotZero_ValueNonZero()
    {
        assertThat(factory.createBuilderForClass(NotZeroObjectBuilder.class).intValue(1).build().getIntValue(), is(1));
    }

    @Test
    public void testNotZero_ValueNull()
    {
        assertThat(factory.createBuilderForClass(NotZeroNullObjectBuilder.class).integerValue(null).build()
                .getIntegerValue(), is((Integer) null));
    }

    public interface NotZeroObjectBuilder
    {
        NotZeroObjectBuilder intValue(@Not({ ZERO }) int value);

        NotZeroObject build();
    }

    public interface NotZeroObject
    {
        int getIntValue();
    }

    public interface NotZeroNullObjectBuilder
    {
        NotZeroNullObjectBuilder integerValue(@Not({ ZERO }) Integer value);

        NotZeroNullObject build();
    }

    public interface NotZeroNullObject
    {
        Integer getIntegerValue();
    }
}
