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
 * @author Prateek Kansal
 *
 */
@SuppressWarnings({ "javadoc" })
public class NotNegativeTest
{
    private final DynamicBuilderFactory factory = new DynamicBuilderFactory();

    @Test
    public void testNotNegative_ValueNegative()
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
    
    @Test
    public void testNotNegative_ValueNull()
    {   
        assertThat(factory.createBuilderForClass(NotNegativeNullObjectBuilder.class).integerValue(null).build()
                .getIntegerValue(), is((Integer) null));
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
    
    private interface NotNegativeNullObjectBuilder
    {
        NotNegativeNullObjectBuilder integerValue(@Not({ NEGATIVE }) Integer value);

        NotNegativeNullObject build();
    }

    private interface NotNegativeNullObject
    {
        Integer getIntegerValue();
    }
}