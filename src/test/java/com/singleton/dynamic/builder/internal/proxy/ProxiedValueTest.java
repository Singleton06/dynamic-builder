package com.singleton.dynamic.builder.internal.proxy;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Method;

import org.junit.Test;

/**
 * Test class for {@link ProxiedValue}.
 * 
 * @author Dustin Singleton
 */
@SuppressWarnings({ "javadoc" })
public class ProxiedValueTest
{
    @Test(expected = IllegalArgumentException.class)
    public void testConstructor_nullMethod()
    {
        new ProxiedValue(null, "value");
    }

    @Test
    public void testConstructor_validValues()
    {
        Method method = ProxiedValue.class.getMethods()[0];
        String value = "value";

        ProxiedValue proxiedValue = new ProxiedValue(method, value);

        assertThat(proxiedValue.getBuilderMethod(), is(method));
        assertThat(proxiedValue.getValue(), is((Object) value));
    }

    public interface TestInterface
    {
        void method();
    }
}
