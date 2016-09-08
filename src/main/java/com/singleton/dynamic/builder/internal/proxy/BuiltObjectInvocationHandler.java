package com.singleton.dynamic.builder.internal.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.singleton.dynamic.builder.defaults.DefaultProvider;

public class BuiltObjectInvocationHandler implements InvocationHandler
{
    private final DefaultProvider defaultProvider = new DefaultProvider();
    private final Map<String, Object> valueMap;

    public BuiltObjectInvocationHandler(Map<String, Object> valueMap)
    {
        this.valueMap = convertToGetterMethod(valueMap);
    }

    private Map<String, Object> convertToGetterMethod(Map<String, Object> originalMap)
    {
        Map<String, Object> convertedMap = new HashMap<String, Object>();
        for (String methodKey : originalMap.keySet())
        {
            String firstCharacter = methodKey.substring(0, 1).toUpperCase();
            String newMethodName = "get" + firstCharacter + methodKey.substring(1);
            convertedMap.put(newMethodName, originalMap.get(methodKey));
        }
        return convertedMap;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
    {
        if (valueMap.containsKey(method.getName()))
        {
            return valueMap.get(method.getName());
        }

        return defaultProvider.getDefaultValue(method);
    }
}
