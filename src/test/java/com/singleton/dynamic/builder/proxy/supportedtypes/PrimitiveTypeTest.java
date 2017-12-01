package com.singleton.dynamic.builder.proxy.supportedtypes;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.singleton.dynamic.builder.proxy.ProxyBuilderFactory;

/**
 * Test class to ensure that all primitive types are able to be set and
 * retrieved.
 * 
 * @author Dustin Singleton
 */
@SuppressWarnings("javadoc")
public class PrimitiveTypeTest
{
    private final ProxyBuilderFactory factory = new ProxyBuilderFactory();

    @Test
    public void testByteValues()
    {
        byte value = 1;

        ByteBuilder builder = factory.createBuilderForClass(ByteBuilder.class);

        assertThat(builder.value(value).build().getValue(), is(value));
    }

    @Test
    public void testCharValue()
    {
        char value = 'b';

        CharBuilder builder = factory.createBuilderForClass(CharBuilder.class);

        assertThat(builder.value(value).build().getValue(), is(value));
    }

    @Test
    public void testShortValue()
    {
        short value = 1;

        ShortBuilder builder = factory.createBuilderForClass(ShortBuilder.class);

        assertThat(builder.value(value).build().getValue(), is(value));
    }

    @Test
    public void testIntValue()
    {
        int value = 1;

        IntBuilder builder = factory.createBuilderForClass(IntBuilder.class);

        assertThat(builder.value(value).build().getValue(), is(value));
    }

    @Test
    public void testLongValue()
    {
        long value = 1L;

        LongBuilder builder = factory.createBuilderForClass(LongBuilder.class);

        assertThat(builder.value(value).build().getValue(), is(value));
    }

    @Test
    public void testFloatValue()
    {
        float value = 1.1f;

        FloatBuilder builder = factory.createBuilderForClass(FloatBuilder.class);

        assertThat(builder.value(value).build().getValue(), is(value));
    }

    @Test
    public void testDoubleValue()
    {
        double value = 1.1;

        DoubleBuilder builder = factory.createBuilderForClass(DoubleBuilder.class);

        assertThat(builder.value(value).build().getValue(), is(value));
    }

    @Test
    public void testBooleanValue()
    {
        boolean value = true;

        BooleanBuilder builder = factory.createBuilderForClass(BooleanBuilder.class);

        assertThat(builder.value(value).build().getValue(), is(value));
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