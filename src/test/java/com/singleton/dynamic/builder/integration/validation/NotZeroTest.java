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
 * @author Prateek Kansal
 *
 */
@SuppressWarnings({ "javadoc" })
public class NotZeroTest
{
    private final DynamicBuilderFactory factory = new DynamicBuilderFactory();

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
                .getIntegerValue(),
                is((Integer) null));
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

    private interface NotZeroNullObjectBuilder
    {
        NotZeroNullObjectBuilder integerValue(@Not({ ZERO }) Integer value);

        NotZeroNullObject build();
    }

    private interface NotZeroNullObject
    {
        Integer getIntegerValue();
    }
}
