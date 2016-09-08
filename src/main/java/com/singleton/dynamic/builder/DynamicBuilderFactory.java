package com.singleton.dynamic.builder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import com.singleton.dynamic.builder.internal.proxy.BuilderInvocationHandler;

public class DynamicBuilderFactory
{
    public <T> T createBuilderForClass(Class<T> clazz)
    {
        InvocationHandler handler = new BuilderInvocationHandler(clazz);

        @SuppressWarnings("unchecked")
        T proxy = (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[] { clazz }, handler);
        return proxy;
    }
}
