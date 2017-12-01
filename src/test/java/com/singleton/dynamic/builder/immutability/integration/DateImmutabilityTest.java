package com.singleton.dynamic.builder.immutability.integration;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.junit.Test;

import com.singleton.dynamic.builder.annotation.Immutable;
import com.singleton.dynamic.builder.proxy.ProxyBuilderFactory;

/**
 * Test class to ensure that immutability functionality works correctly for Date objects.
 * 
 * @author Dustin Singleton
 */
@SuppressWarnings({ "javadoc" })
public class DateImmutabilityTest
{
    private final ProxyBuilderFactory factory = new ProxyBuilderFactory();
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

    @Test
    public void testBuilderImmutability_withNonImmutableAnnotation()
    {
        long originalTime = 1000L;
        long modifyTime = 1001L;
        long returnedObjectModifyTime = 1002L;
        Date dateToBeModified = new Date(originalTime);

        BuiltObject builtObject = builder.nonImmutableDateValue(dateToBeModified).build();

        dateToBeModified.setTime(modifyTime);
        assertThat(builtObject.getNonImmutableDateValue().getTime(), is(modifyTime));

        builtObject.getNonImmutableDateValue().setTime(returnedObjectModifyTime);
        assertThat(builtObject.getNonImmutableDateValue().getTime(), is(returnedObjectModifyTime));
    }

    @Test
    public void testBuilderImmutability_nullValueDoesNothing()
    {
        BuiltObject builtObject = builder.immutableDateValue(null).build();

        assertThat(builtObject.getImmutableDateValue(), is(nullValue()));
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
