package com.singleton.dynamic.builder.proxy.internal.invocation;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Method;

import org.junit.Test;

import com.singleton.dynamic.builder.proxy.internal.invocation.ProxiedValue;

/**
 * Test class for {@link ProxiedValue}.
 * 
 * @author Dustin Singleton
 */
@SuppressWarnings({ "javadoc", "nls" })
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
