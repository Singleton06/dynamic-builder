package com.singleton.dynamic.builder.proxy.internal.invocation;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.singleton.dynamic.builder.annotation.DefaultValue;
import com.singleton.dynamic.builder.defaults.DefaultProvider;
import com.singleton.dynamic.builder.proxy.internal.valueprovider.BuiltObjectValueProvider;

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
    private final Class<?> builderClass;

    /**
     * Constructs a new {@link BuiltObjectInvocationHandler} with the provided {@code valueMap}.
     * 
     * @param valueMap
     *            The map of values keyed by the method names.
     * @param builderClass
     *            The interface that defines the builder.
     */
    public BuiltObjectInvocationHandler(Map<String, ProxiedValue> valueMap, Class<?> builderClass)
    {
        this(valueMap, new BuiltObjectValueProvider(), builderClass);
    }

    BuiltObjectInvocationHandler(Map<String, ProxiedValue> valueMap, BuiltObjectValueProvider valueProvider,
            Class<?> builderClass)
    {
        this.valueMap = convertToGetterMethod(valueMap);
        this.valueProvider = valueProvider;
        this.builderClass = builderClass;
    }

    private static Map<String, ProxiedValue> convertToGetterMethod(Map<String, ProxiedValue> originalMap)
    {
        Map<String, ProxiedValue> convertedMap = new HashMap<String, ProxiedValue>();
        for (String methodKey : originalMap.keySet())
        {
            String firstCharacter = methodKey.substring(0, 1).toUpperCase();
            String newMethodName = "get" + firstCharacter + methodKey.substring(1); //$NON-NLS-1$
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

        return getDefaultValue(method);
    }

    private Object getDefaultValue(Method getterMethod)
    {
        String getterMethodName = getterMethod.getName();
        String setterMetthedName = getterMethodName.substring(3);

        Method[] builderMethods = builderClass.getDeclaredMethods();
        for (Method method : builderMethods)
        {
            if (method.getName().equalsIgnoreCase(setterMetthedName))
            {
                Annotation annotation = isMethodContainAnnotationClass(method, DefaultValue.class);
                if (annotation != null)
                {
                    return defaultProvider.getDefaultValue(method.getParameterTypes()[0],
                            ((DefaultValue) annotation).value());
                }
            }
        }

        return defaultProvider.getDefaultValue(getterMethod);
    }

    private static Annotation isMethodContainAnnotationClass(Method method, Class<? extends Annotation> annotationType)
    {
        for (Annotation singleAnnotation : method.getParameterAnnotations()[0])
        {
            if (singleAnnotation.annotationType().equals(annotationType))
            {
                return singleAnnotation;
            }
        }
        return null;
    }
}
