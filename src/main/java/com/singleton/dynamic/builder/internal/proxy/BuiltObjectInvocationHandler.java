package com.singleton.dynamic.builder.internal.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.singleton.dynamic.builder.defaults.DefaultProvider;
import com.singleton.dynamic.builder.internal.valueprovider.BuiltObjectValueProvider;

/**
 * <p>
 * Invocation handler for any built object interfaces that are provided. Basically this class will be responsible for
 * handling any calls that are made on methods that appear on the built object's interface.
 * </p>
 * 
 * @author Dustin Singleton
 */
public class BuiltObjectInvocationHandler implements InvocationHandler
{
    private final DefaultProvider defaultProvider = new DefaultProvider();
    private final Map<String, ProxiedValue> valueMap;
    private final BuiltObjectValueProvider valueProvider;

    /**
     * Constructs a new {@link BuiltObjectInvocationHandler} with the provided {@code valueMap}.
     * 
     * @param valueMap
     *            The map of values keyed by the method names.
     */
    public BuiltObjectInvocationHandler(Map<String, ProxiedValue> valueMap)
    {
        this(valueMap, new BuiltObjectValueProvider());
    }

    BuiltObjectInvocationHandler(Map<String, ProxiedValue> valueMap, BuiltObjectValueProvider valueProvider)
    {
        this.valueMap = convertToGetterMethod(valueMap);
        this.valueProvider = valueProvider;
    }

    private Map<String, ProxiedValue> convertToGetterMethod(Map<String, ProxiedValue> originalMap)
    {
        Map<String, ProxiedValue> convertedMap = new HashMap<String, ProxiedValue>();
        for (String methodKey : originalMap.keySet())
        {
            String firstCharacter = methodKey.substring(0, 1).toUpperCase();
            String newMethodName = "get" + firstCharacter + methodKey.substring(1);
            convertedMap.put(newMethodName, originalMap.get(methodKey));
        }
        return convertedMap;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
    {
        if (valueMap.containsKey(method.getName()))
        {
            ProxiedValue proxiedValue = valueMap.get(method.getName());
            return valueProvider.getValue(proxiedValue);
        }

        return defaultProvider.getDefaultValue(method);
    }
}
