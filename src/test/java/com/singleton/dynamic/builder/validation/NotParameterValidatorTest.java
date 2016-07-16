package com.singleton.dynamic.builder.validation;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.junit.Test;

/**
 * Test class for {@link NotParameterValidator}.
 * 
 * @author Dustin Singleton
 */
@SuppressWarnings({ "javadoc" })
public class NotParameterValidatorTest
{
    private final Method method = InterfaceWithMethod.class.getMethods()[0];

    @Test
    public void testNotNull_nullValue()
    {
        try
        {
            NotParameterValidator.NULL.validate(null, method);
            fail("Expected IllegalArgumentException but none was thrown when null validation occurred with null value");
        } catch (IllegalArgumentException e)
        {
            assertThat(e.getMessage(), is(method.getName() + " was provided null, but non null values are required"));
        }
    }

    @Test
    public void testNotNull_nonNullValueString()
    {
        NotParameterValidator.NULL.validate("non-null value", method);
    }

    @Test
    public void testNotNull_nonNullListCollection()
    {
        NotParameterValidator.NULL.validate(Collections.<String> emptyList(), method);
    }

    @Test
    public void testNotNull_emptyValue()
    {
        NotParameterValidator.NULL.validate("", method);
    }

    @Test
    public void testNotEmpty_emptyStringValue()
    {
        try
        {
            NotParameterValidator.EMPTY.validate("", method);
            fail("Expected IllegalArgumentException but none was thrown when empty validation occurred with empty value");
        } catch (IllegalArgumentException e)
        {
            assertThat(e.getMessage(), is(method.getName() + " was provided empty, but non empty values are required"));
        }
    }

    @Test
    public void testNotEmpty_nonEmptyString()
    {
        NotParameterValidator.EMPTY.validate("non-empty-string", method);
    }

    @Test
    public void testNotEmpty_null()
    {
        NotParameterValidator.EMPTY.validate(null, method);
    }

    @Test
    public void testNotEmpty_emptyCollectionValue()
    {
        Collection<Long> longCollection = Arrays.asList();
        try
        {
            NotParameterValidator.EMPTY.validate(longCollection, method);
            fail("Expected IllegalArgumentException but none was thrown when empty validation occurred with empty list");
        } catch (IllegalArgumentException e)
        {
            assertThat(e.getMessage(), is(method.getName() + " was provided an empty collection, but a non-empty collection is required"));
        }
    }

    @Test
    public void testNotEmpty_listWithValues()
    {
        NotParameterValidator.EMPTY.validate(Arrays.asList(1L), method);
    }

    @Test
    public void testNotEmpty_emptyListValue()
    {
        try
        {
            NotParameterValidator.EMPTY.validate(Collections.<Long> emptyList(), method);
            fail("Expected IllegalArgumentException but none was thrown when empty validation occurred with empty list");
        } catch (IllegalArgumentException e)
        {
            assertThat(e.getMessage(), is(method.getName() + " was provided an empty collection, but a non-empty collection is required"));
        }
    }

    private interface InterfaceWithMethod
    {
        void method();
    }
}