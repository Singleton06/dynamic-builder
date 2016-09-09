package com.singleton.dynamic.builder.defaults;

import static com.singleton.dynamic.builder.defaults.DefaultValueType.CURRENT_DATE_TIME;
import static com.singleton.dynamic.builder.defaults.DefaultValueType.EMPTY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.singleton.dynamic.builder.annotation.DefaultValue;

/**
 * Test class for {@link DefaultProvider}.
 * 
 * @author Dustin Singleton
 * @author Prateek Kansal
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
    
    @Test
    public void testGetDefaultValue_String()
    {   
        Method method = EmptyStringAnnotatedObjectBuilder.class.getMethods()[0];

        assertEquals("", defaultProvider.getDefaultValue(method.getParameterTypes()[0], EMPTY));
    }

    @Test
    public void testGetDefaultValue_Collection()
    {
        Method method = EmptyCollectionAnnotatedObjectBuilder.class.getMethods()[0];

        assertEquals(new ArrayList(), defaultProvider.getDefaultValue(method.getParameterTypes()[0], EMPTY));
    }

    @Test
    public void testGetDefaultValue_Set()
    {
        Method method = EmptySetAnnotatedObjectBuilder.class.getMethods()[0];

        assertEquals(new HashSet(), defaultProvider.getDefaultValue(method.getParameterTypes()[0], EMPTY));
    }

    @Test
    public void testGetDefaultValue_Date() throws InterruptedException
    {
        Method method = CurrentDateTimeAnnotatedObjectBuilder.class.getMethods()[0];

        Date startDate = new Date();
        Thread.sleep(1);

        Date originalDate = (Date) defaultProvider.getDefaultValue(method.getParameterTypes()[0], CURRENT_DATE_TIME);
        Thread.sleep(1);

        Date endDate = new Date();
        
        assertTrue(originalDate.after(startDate));
        assertTrue(originalDate.before(endDate));
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

    private interface EmptyStringAnnotatedObjectBuilder
    {
        Object stringValue(@DefaultValue(EMPTY) String value);
    }
    
    private interface EmptyCollectionAnnotatedObjectBuilder
    {
        Object collectionValue(@DefaultValue(EMPTY) Collection<?> value);
    }
    
    private interface EmptySetAnnotatedObjectBuilder
    {
        Object setValue(@DefaultValue(EMPTY) Set<?> value);
    }
    
    private interface CurrentDateTimeAnnotatedObjectBuilder
    {
        Object dateValue(@DefaultValue(CURRENT_DATE_TIME) Date value);
    }
}