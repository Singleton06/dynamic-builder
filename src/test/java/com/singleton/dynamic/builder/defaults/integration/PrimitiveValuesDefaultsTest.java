package com.singleton.dynamic.builder.defaults.integration;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.singleton.dynamic.builder.DynamicBuilderFactory;

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

    private final DynamicBuilderFactory factory = DynamicBuilderFactory.getInstance();

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

    public interface BooleanBuilder
    {
        BooleanBuilder value(boolean id);

        BooleanObject build();
    }

    public interface BooleanObject
    {
        boolean getValue();
    }

    public interface DoubleBuilder
    {
        DoubleBuilder value(double id);

        DoubleObject build();
    }

    public interface DoubleObject
    {
        double getValue();
    }

    public interface FloatBuilder
    {
        FloatBuilder value(float id);

        FloatObject build();
    }

    public interface FloatObject
    {
        float getValue();
    }

    public interface LongBuilder
    {
        LongBuilder value(long id);

        LongObject build();
    }

    public interface LongObject
    {
        long getValue();
    }

    public interface IntBuilder
    {
        IntBuilder value(int id);

        IntObject build();
    }

    public interface IntObject
    {
        int getValue();
    }

    public interface ShortBuilder
    {
        ShortBuilder value(short id);

        ShortObject build();
    }

    public interface ShortObject
    {
        short getValue();
    }

    public interface CharBuilder
    {
        CharBuilder value(char id);

        CharObject build();
    }

    public interface CharObject
    {
        char getValue();
    }

    public interface ByteBuilder
    {
        ByteBuilder value(byte id);

        ByteObject build();
    }

    public interface ByteObject
    {
        byte getValue();
    }
}
