package com.singleton.dynamic.builder.internal.proxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import com.singleton.dynamic.builder.annotation.Not;
import com.singleton.dynamic.builder.validation.NotParameterValidator;

public class BuilderInvocationHandler implements InvocationHandler
{
    private final Map<String, Object> valueMap = new HashMap<String, Object>();

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
    {
        if (!method.getName().equals("build") && args != null && args.length == 1)
        {
            Object parameterValue = args[0];
            performValidation(method, parameterValue);
            valueMap.put(method.getName(), parameterValue);
            return proxy;
        }
        else if (method.getName().equals("build") && args == null)
        {
            Class<?> returnClass = method.getReturnType();
            InvocationHandler handler = new BuiltObjectInvocationHandler(valueMap);
            return Proxy.newProxyInstance(returnClass.getClassLoader(), new Class<?>[] { returnClass }, handler);
        }

        System.out.println("Invalid method invoked " + method.getName());
        return null;
    }

    private void performValidation(Method method, Object parameterValue)
    {
        // pull off the annotations for the first parameter, given that
        for (Annotation singleAnnotation : method.getParameterAnnotations()[0])
        {
            if (singleAnnotation.annotationType().equals(Not.class))
            {
                for (NotParameterValidator singleValidator : ((Not) singleAnnotation).value())
                {
                    singleValidator.validate(parameterValue, method);
                }
            }
        }
    }
}