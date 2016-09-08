package com.singleton.dynamic.builder.integration.immutability;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.junit.Test;

import com.singleton.dynamic.builder.DynamicBuilderFactory;
import com.singleton.dynamic.builder.annotation.Immutable;

/**
 * Test class to ensure that immutability functionality works correctly for Date objects.
 * 
 * @author Dustin Singleton
 */
@SuppressWarnings({ "javadoc" })
public class DateImmutabilityTest
{
    private final DynamicBuilderFactory factory = new DynamicBuilderFactory();
    private final Builder builder = factory.createBuilderForClass(Builder.class);

    @Test
    public void testBuilderImmutability_withImmutableAnnotation()
    {
        long originalTime = 1000L;
        long modifyTime = 1001L;
        Date dateToBeModified = new Date(originalTime);

        BuiltObject builtObject = builder.immutableDateValue(dateToBeModified).build();

        dateToBeModified.setTime(modifyTime);
        assertThat(builtObject.getImmutableDateValue().getTime(), is(originalTime));

        builtObject.getImmutableDateValue().setTime(modifyTime);
        assertThat(builtObject.getImmutableDateValue().getTime(), is(originalTime));
    }

    private interface Builder
    {
        Builder nonImmutableDateValue(Date date);

        Builder immutableDateValue(@Immutable Date date);

        BuiltObject build();
    }

    private interface BuiltObject
    {
        Date getNonImmutableDateValue();

        Date getImmutableDateValue();
    }
}
