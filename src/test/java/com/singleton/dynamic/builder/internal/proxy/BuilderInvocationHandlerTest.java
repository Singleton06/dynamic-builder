package com.singleton.dynamic.builder.internal.proxy;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.sameInstance;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.junit.Test;

/**
 * Test class for {@link BuilderInvocationHandler}.
 * 
 * @author Dustin Singleton
 */
@SuppressWarnings({ "javadoc" })
public class BuilderInvocationHandlerTest
{
    @Test
    public void testInvoke_setterMethodReturnsHandler() throws Throwable
    {
        String stringValueMethodName = "stringValue";
        Method stringValueMethod = Builder.class.getMethod(stringValueMethodName, String.class);
        assertThat(stringValueMethod.getName(), is(stringValueMethodName));

        InvocationHandler handler = new BuilderInvocationHandler(Builder.class);

        Object builder = handler.invoke(handler, stringValueMethod, new String[] { "testValue" });
        assertThat(handler, sameInstance(builder));
    }

    @Test
    public void testInvoke_setterMethodAndBuild() throws Throwable
    {
        String stringValueMethodName = "stringValue";
        Method stringValueMethod = Builder.class.getMethod(stringValueMethodName, String.class);
        assertThat(stringValueMethod.getName(), is(stringValueMethodName));

        String buildMethodName = "build";
        Method buildMethod = Builder.class.getMethod(buildMethodName);
        assertThat(buildMethod.getName(), is(buildMethodName));

        InvocationHandler handler = new BuilderInvocationHandler(Builder.class);
        handler.invoke(handler, stringValueMethod, new String[] { "testValue" });
        BuiltObject builtObject = (BuiltObject) handler.invoke(handler, buildMethod, null);

        assertThat(builtObject.getStringValue(), is("testValue"));
    }

    @Test
    public void testInvoke_nullReturnedWhenMethodNotFound() throws Throwable
    {
        String nonMatchingMethodName = "someMethodThatDoesNotMatchTheBuilderMethod";
        Method nonMatchingMethod = NotABuilder.class.getMethod(nonMatchingMethodName);
        assertThat(nonMatchingMethod.getName(), is(nonMatchingMethodName));

        InvocationHandler handler = new BuilderInvocationHandler(NotABuilder.class);
        Object returnedValue = handler.invoke(handler, nonMatchingMethod, null);

        assertThat(returnedValue, is(nullValue()));
    }

    @Test
    public void testInvoke_nullReturnedWhenBuildWithArgumentsCalled() throws Throwable
    {
        String buildMethodName = "build";
        Method buildMethod = Builder.class.getMethod(buildMethodName);
        assertThat(buildMethod.getName(), is(buildMethodName));

        InvocationHandler handler = new BuilderInvocationHandler(Builder.class);
        Object returnedValue = handler.invoke(handler, buildMethod, new String[] { "argument" });

        assertThat(returnedValue, is(nullValue()));
    }

    @Test
    public void testInvoke_nullReturnedWhenSetterMethodWithNoArgumentsCalled() throws Throwable
    {
        String setterMethodWithNoArgsMethodName = "setterMethodWithNoArgs";
        Method setterMethodWithNoArgs = Builder.class.getMethod(setterMethodWithNoArgsMethodName);
        assertThat(setterMethodWithNoArgs.getName(), is(setterMethodWithNoArgsMethodName));

        InvocationHandler handler = new BuilderInvocationHandler(Builder.class);
        Object returnedValue = handler.invoke(handler, setterMethodWithNoArgs, null);

        assertThat(returnedValue, is(nullValue()));
    }

    @Test
    public void testInvoke_nullReturnedWhenSetterMethodWithTwoArguments() throws Throwable
    {
        String setterMethodWithNoArgsMethodName = "setterMethodWithTwoArguments";
        Method setterMethodWithNoArgs = Builder.class.getMethod(setterMethodWithNoArgsMethodName, String.class,
                String.class);
        assertThat(setterMethodWithNoArgs.getName(), is(setterMethodWithNoArgsMethodName));

        InvocationHandler handler = new BuilderInvocationHandler(Builder.class);
        Object returnedValue = handler.invoke(handler, setterMethodWithNoArgs, new Object[] { "String1", "String2" });

        assertThat(returnedValue, is(nullValue()));
    }

    private interface NotABuilder
    {
        void someMethodThatDoesNotMatchTheBuilderMethod();
    }

    private interface Builder
    {
        Builder setterMethodWithNoArgs();

        Builder setterMethodWithTwoArguments(String arg1, String arg2);

        Builder stringValue(String value);

        BuiltObject build();
    }

    private interface BuiltObject
    {
        String getStringValue();
    }
}
