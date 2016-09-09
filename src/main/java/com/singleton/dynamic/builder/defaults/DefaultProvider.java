package com.singleton.dynamic.builder.defaults;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.singleton.dynamic.builder.annotation.DefaultValue;

/**
 * API to provide default values based on the provided method.
 * 
 * @author Dustin Singleton
 */
public class DefaultProvider
{
    private static final String BOOLEAN = "boolean";
    private static final String CHAR = "char";
    private static final String DOUBLE = "double";
    private static final String FLOAT = "float";
    private static final String LONG = "long";
    private static final String INT = "int";
    private static final String SHORT = "short";

    /**
     * Gets the default value for the method specified. While this <strong>might</strong> be the java default value, it
     * does not necessarily mean that it <strong>must</strong> be the java default. Other aspects of the method (such as
     * annotations) could dictate that a different default value should be used.
     * 
     * @param method
     *            The method that the default value should be retrieved for.
     *
     * @return the default value for the method specified.
     */
    public Object getDefaultValue(Method method)
    {   
        Class<?> returnType = method.getReturnType();
        if (returnType.isPrimitive())
        {
            return getPrimitiveDefaultValue(returnType);
        }

        return null;
    }

    /**
     * Gets the default value for the specified parameter type. This will only return value for non-primitive types
     * specified using the {@link DefaultValue annotation} {@link DefaultValue value type}.
     * 
     * @param parameterType
     *            The type of the parameter.
     * @param defaultValueType
     *            The {@link DefaultValueType value} type of the {@link DefaultValue annotation}.
     * @return The default value specified for the {@code defaultValueType}.
     */
    public Object getDefaultValue(Class<?> parameterType, DefaultValueType defaultValueType)
    {
        return getNonPrimitiveDefaultValue(parameterType, defaultValueType);
    }

    private static Object getPrimitiveDefaultValue(Class<?> returnType)
    {
        String returnTypeName = returnType.getName();

        if (INT.equals(returnTypeName))
        {
            return 0;
        }
        else if (LONG.equals(returnTypeName))
        {
            return 0L;
        }
        else if (BOOLEAN.equals(returnTypeName))
        {
            return false;
        }
        else if (DOUBLE.equals(returnTypeName))
        {
            return 0.0;
        }
        else if (SHORT.equals(returnTypeName))
        {
            return (short) 0;
        }
        else if (FLOAT.equals(returnTypeName))
        {
            return 0.0f;
        }
        else if (CHAR.equals(returnTypeName))
        {
            return '\u0000';
        }
        else
        {
            // Return byte, given that it is the only other primitive type
            return (byte) 0;
        }
    }

    @SuppressWarnings("rawtypes")
    private static Object getNonPrimitiveDefaultValue(Class<?> paramType, DefaultValueType defaultValueType)
    {   
        if (String.class.isAssignableFrom(paramType))
        {
            if (DefaultValueType.EMPTY.equals(defaultValueType))
            {
                return "";
            }
        }

        if (Collection.class.equals(paramType))
        {
            if (DefaultValueType.EMPTY.equals(defaultValueType))
            {
                return new ArrayList();
            }
        }

        if (Date.class.isAssignableFrom(paramType))
        {
            if (DefaultValueType.CURRENT_DATE_TIME.equals(defaultValueType))
            {
                return new Date();
            }
        }

        if (Set.class.isAssignableFrom(paramType))
        {
            if (DefaultValueType.EMPTY.equals(defaultValueType))
            {
                return new HashSet();
            }
        }

        return null;
    }
}
