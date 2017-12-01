package com.singleton.dynamic.builder.defaults.integration;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.singleton.dynamic.builder.proxy.ProxyBuilderFactory;

/**
 * Test class to ensure that primitives values (when not set) will still the
 * java default value.
 * 
 * @author Dustin Singleton
 */
@SuppressWarnings("javadoc")
public class PrimitiveValuesDefaultsTest
{
    private boolean uninitializedBooleanValue;
    private double uninitializedDoubleValue;;
    private float uninitializedFloatValue;
    private long uninitializedLongValue;
    private int uninitializedIntValue;
    private short uninitializedShortValue;
    private char uninitializedCharValue;
    private byte uninitializedByteValue;

    private final ProxyBuilderFactory factory = new ProxyBuilderFactory();

    @Test
    public void testBooleanDefaultValues()
    {
        BooleanBuilder builder = factory.createBuilderForClass(BooleanBuilder.class);

        assertThat(builder.build().getValue(), is(uninitializedBooleanValue));
    }

    @Test
    public void testDoubleDefaultValues()
    {
        DoubleBuilder builder = factory.createBuilderForClass(DoubleBuilder.class);

        assertThat(builder.build().getValue(), is(uninitializedDoubleValue));
    }

    @Test
    public void testFloatDefaultValues()
    {
        FloatBuilder builder = factory.createBuilderForClass(FloatBuilder.class);

        assertThat(builder.build().getValue(), is(uninitializedFloatValue));
    }

    @Test
    public void testLongDefaultValues()
    {
        LongBuilder builder = factory.createBuilderForClass(LongBuilder.class);

        assertThat(builder.build().getValue(), is(uninitializedLongValue));
    }

    @Test
    public void testIntDefaultValues()
    {
        IntBuilder builder = factory.createBuilderForClass(IntBuilder.class);

        assertThat(builder.build().getValue(), is(uninitializedIntValue));
    }

    @Test
    public void testShortDefaultValues()
    {
        ShortBuilder builder = factory.createBuilderForClass(ShortBuilder.class);

        assertThat(builder.build().getValue(), is(uninitializedShortValue));
    }
    
    @Test
    public void testCharDefaultValues()
    {
        CharBuilder builder = factory.createBuilderForClass(CharBuilder.class);

        assertThat(builder.build().getValue(), is(uninitializedCharValue));
    }
    
    @Test
    public void testByteDefaultValues()
    {
        ByteBuilder builder = factory.createBuilderForClass(ByteBuilder.class);

        assertThat(builder.build().getValue(), is(uninitializedByteValue));
    }

    private interface BooleanBuilder
    {
        BooleanBuilder value(boolean id);

        BooleanObject build();
    }

    private interface BooleanObject
    {
        boolean getValue();
    }

    private interface DoubleBuilder
    {
        DoubleBuilder value(double id);

        DoubleObject build();
    }

    private interface DoubleObject
    {
        double getValue();
    }

    private interface FloatBuilder
    {
        FloatBuilder value(float id);

        FloatObject build();
    }

    private interface FloatObject
    {
        float getValue();
    }

    private interface LongBuilder
    {
        LongBuilder value(long id);

        LongObject build();
    }

    private interface LongObject
    {
        long getValue();
    }

    private interface IntBuilder
    {
        IntBuilder value(int id);

        IntObject build();
    }

    private interface IntObject
    {
        int getValue();
    }

    private interface ShortBuilder
    {
        ShortBuilder value(short id);

        ShortObject build();
    }

    private interface ShortObject
    {
        short getValue();
    }

    private interface CharBuilder
    {
        CharBuilder value(char id);

        CharObject build();
    }

    private interface CharObject
    {
        char getValue();
    }

    private interface ByteBuilder
    {
        ByteBuilder value(byte id);

        ByteObject build();
    }

    private interface ByteObject
    {
        byte getValue();
    }
}
