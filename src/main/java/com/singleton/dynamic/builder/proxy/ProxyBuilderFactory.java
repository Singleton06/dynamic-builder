package com.singleton.dynamic.builder.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import com.singleton.dynamic.builder.DynamicBuilderFactory;
import com.singleton.dynamic.builder.proxy.internal.invocation.BuilderInvocationHandler;

/**
 * <p>
 * Creates builders using {@link Proxy}.
 * </p>
 */
public class ProxyBuilderFactory implements DynamicBuilderFactory
{
    @Override
    public <T> T createBuilderForClass(Class<T> clazz)
    {
        InvocationHandler handler = new BuilderInvocationHandler(clazz);

        @SuppressWarnings("unchecked")
        T proxy = (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[] { clazz }, handler);
        return proxy;
    }
}
