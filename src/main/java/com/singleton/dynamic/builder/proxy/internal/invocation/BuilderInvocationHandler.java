package com.singleton.dynamic.builder.proxy.internal.invocation;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.singleton.dynamic.builder.annotation.Not;
import com.singleton.dynamic.builder.annotation.Required;
import com.singleton.dynamic.builder.proxy.internal.valueprovider.BuilderValueProvider;
import com.singleton.dynamic.builder.validation.NotParameterValidator;

/**
 * <p>
 * Invocation handler for any builder interfaces that are provided. Basically this class will be responsible for
 * handling any calls that are made on methods that appear on the builder's interface.
 * </p>
 * 
 * @author Dustin Singleton
 */
public class BuilderInvocationHandler implements InvocationHandler
{
    private final Class<?> builderClass;
    private final Map<String, ProxiedValue> valueMap = new HashMap<String, ProxiedValue>();
    private final BuilderValueProvider valueProvider;

    /**
     * Default constructor.
     * 
     * @param builderClass
     *            The class that is used as the builder.
     */
    public BuilderInvocationHandler(Class<?> builderClass)
    {
        this(new BuilderValueProvider(), builderClass);
    }

    BuilderInvocationHandler(BuilderValueProvider valueProvider, Class<?> builderClass)
    {
        this.valueProvider = valueProvider;
        this.builderClass = builderClass;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
    {
        if (!method.getName().equals("build") && args != null && args.length == 1) //$NON-NLS-1$
        {
            Object parameterValue = args[0];
            performValidation(method, parameterValue);
            Object updatedValue = valueProvider.getValue(method, parameterValue);
            valueMap.put(method.getName(), new ProxiedValue(method, updatedValue));
            return proxy;
        }
        else if (method.getName().equals("build") && args == null) //$NON-NLS-1$
        {
            performRequiredMethodValidation(builderClass.getDeclaredMethods());
            Class<?> returnClass = method.getReturnType();
            InvocationHandler handler = new BuiltObjectInvocationHandler(valueMap, builderClass);
            return Proxy.newProxyInstance(returnClass.getClassLoader(), new Class<?>[] { returnClass }, handler);
        }

        Logger.getAnonymousLogger().log(Level.SEVERE, "Invalid method invoked " + method.getName()); //$NON-NLS-1$
        return null;
    }

    private static void performValidation(Method method, Object parameterValue)
    {
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

    private void performRequiredMethodValidation(Method[] methods)
    {
        for (Method method : methods)
        {
            if (method.getAnnotation(Required.class) != null)
            {
                if (!valueMap.containsKey(method.getName()))
                {
                    throw new IllegalStateException(
                            method.getName() + " was not called on this builder class " + builderClass.getName() + "."); //$NON-NLS-1$ //$NON-NLS-2$
                }
            }
        }
    }
}