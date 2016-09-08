package com.singleton.dynamic.builder.internal.valueprovider;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.lang.reflect.Method;
import java.util.Date;

import org.junit.Test;

import com.singleton.dynamic.builder.annotation.Immutable;
import com.singleton.dynamic.builder.internal.proxy.ProxiedValue;

/**
 * Test class for the {@link BuiltObjectValueProvider}.
 * 
 * @author Dustin Singleton
 */
@SuppressWarnings({ "javadoc" })
public class BuiltObjectValueProviderTest
{
    private final BuiltObjectValueProvider valueProvider = new BuiltObjectValueProvider();

    private final String nonImmutableDateValueMethodName = "nonImmutableDateValue";
    private final Method nonImmutableDateMethod = getMethod(nonImmutableDateValueMethodName, Date.class);

    private final String immutableDateValueMethodName = "immutableDateValue";
    private final Method immutableDateMethod = getMethod(immutableDateValueMethodName, Date.class);

    private static Method getMethod(String name, Class<?>... parameterTypes)
    {
        try
        {
            return TestBuilderInterface.class.getMethod(name, parameterTypes);
        }
        catch (Throwable t)
        {
            throw new RuntimeException("Exception while trying to get method name: " + name, t);
        }
    }

    @Test
    public void testGetValue_nonImmutableDate()
    {
        long originalTime = 1000L;
        long modifyTime = 1001L;
        Date dateToBeModified = new Date(originalTime);
        ProxiedValue proxiedValue = new ProxiedValue(nonImmutableDateMethod, dateToBeModified);

        Object value = valueProvider.getValue(proxiedValue);
        dateToBeModified.setTime(modifyTime);

        assertThat(value, is(instanceOf(Date.class)));
        assertThat(((Date) value).getTime(), is(modifyTime));
    }

    @Test
    public void testGetValue_immutableDate()
    {
        long originalTime = 1000L;
        long modifyTime = 1001L;
        Date dateToBeModified = new Date(originalTime);
        ProxiedValue proxiedValue = new ProxiedValue(immutableDateMethod, dateToBeModified);

        Object value = valueProvider.getValue(proxiedValue);
        dateToBeModified.setTime(modifyTime);

        assertThat(value, is(instanceOf(Date.class)));
        assertThat(((Date) value).getTime(), is(originalTime));
    }

    private interface TestBuilderInterface
    {

        TestBuilderInterface nonImmutableDateValue(Date date);

        TestBuilderInterface immutableDateValue(@Immutable Date date);
    }
}