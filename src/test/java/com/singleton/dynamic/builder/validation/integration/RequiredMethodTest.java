package com.singleton.dynamic.builder.validation.integration;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.singleton.dynamic.builder.DynamicBuilderFactory;
import com.singleton.dynamic.builder.annotation.Required;

/**
 * Test class to validate that if any method with {@link Required} annotation on the builder class is not called then it
 * must throw {@link IllegalStateException}.
 * 
 * @author Prateek Kansal
 *
 */
@SuppressWarnings({ "javadoc", "nls" })
public class RequiredMethodTest
{
    private final DynamicBuilderFactory factory = DynamicBuilderFactory.getInstance();

    @Test
    public void testBuilderMethod_IsRequired()
    {
        try
        {
            factory.createBuilderForClass(RequiredMethodObjectBuilder.class).stringValue("Test-String").build();
            fail("Expected IllegalStateException but none was thrown when required method on the builder is not called");
        }
        catch (IllegalStateException e)
        {
            assertThat(e.getMessage(), is("intValue was not called on this builder class "
                    + RequiredMethodObjectBuilder.class.getName() + "."));
        }
    }

    public interface RequiredMethodObjectBuilder
    {

        RequiredMethodObjectBuilder stringValue(String value);

        @Required
        RequiredMethodObjectBuilder intValue(int value);

        RequiredMethodObject build();
    }

    public interface RequiredMethodObject
    {
        String getStringValue();

        int getIntValue();
    }
}