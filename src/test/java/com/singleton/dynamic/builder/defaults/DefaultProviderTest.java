package com.singleton.dynamic.builder.defaults;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Method;

import org.junit.Test;

/**
 * Test class for {@link DefaultProvider}.
 * 
 * @author Dustin Singleton
 */
@SuppressWarnings({ "javadoc" })
public class DefaultProviderTest
{
    private final DefaultProvider defaultProvider = new DefaultProvider();

    private boolean uninitializedBooleanValue;
    private double uninitializedDoubleValue;;
    private float uninitializedFloatValue;
    private long uninitializedLongValue;
    private int uninitializedIntValue;
    private short uninitializedShortValue;
    private char uninitializedCharValue;
    private byte uninitializedByteValue;

    @Test
    public void testGetDefaultValue_byte()
    {
        Method method = ByteObject.class.getMethods()[0];

        assertEquals(uninitializedByteValue, defaultProvider.getDefaultValue(method));
    }

    @Test
    public void testGetDefaultValue_char()
    {
        Method method = CharObject.class.getMethods()[0];

        assertEquals(uninitializedCharValue, defaultProvider.getDefaultValue(method));
    }

    @Test
    public void testGetDefaultValue_short()
    {
        Method method = ShortObject.class.getMethods()[0];

        assertEquals(uninitializedShortValue, defaultProvider.getDefaultValue(method));
    }

    @Test
    public void testGetDefaultValue_int()
    {
        Method method = IntObject.class.getMethods()[0];

        assertEquals(uninitializedIntValue, defaultProvider.getDefaultValue(method));
    }

    @Test
    public void testGetDefaultValue_long()
    {
        Method method = LongObject.class.getMethods()[0];

        assertEquals(uninitializedLongValue, defaultProvider.getDefaultValue(method));
    }

    @Test
    public void testGetDefaultValue_float()
    {
        Method method = FloatObject.class.getMethods()[0];

        assertEquals(uninitializedFloatValue, defaultProvider.getDefaultValue(method));
    }

    @Test
    public void testGetDefaultValue_double()
    {
        Method method = DoubleObject.class.getMethods()[0];

        assertEquals(uninitializedDoubleValue, defaultProvider.getDefaultValue(method));
    }

    @Test
    public void testGetDefaultValue_boolean()
    {
        Method method = BooleanObject.class.getMethods()[0];

        assertEquals(uninitializedBooleanValue, defaultProvider.getDefaultValue(method));
    }

    @Test
    public void testGetDefaultValue_object()
    {
        Method method = ObjectObject.class.getMethods()[0];

        assertEquals(null, defaultProvider.getDefaultValue(method));
    }
    
    private interface BooleanObject
    {
        boolean getValue();
    }

    private interface DoubleObject
    {
        double getValue();
    }

    private interface FloatObject
    {
        float getValue();
    }

    private interface LongObject
    {
        long getValue();
    }

    private interface IntObject
    {
        int getValue();
    }

    private interface ShortObject
    {
        short getValue();
    }

    private interface CharObject
    {
        char getValue();
    }

    private interface ByteObject
    {
        byte getValue();
    }
    
    private interface ObjectObject
    {
        Object getValue();
    }
}