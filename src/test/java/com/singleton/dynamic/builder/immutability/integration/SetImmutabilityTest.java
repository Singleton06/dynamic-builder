package com.singleton.dynamic.builder.immutability.integration;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.Test;

import com.singleton.dynamic.builder.annotation.Immutable;
import com.singleton.dynamic.builder.proxy.ProxyBuilderFactory;

/**
 * Test to ensure that the {@link Immutable} annotation functions as expected.
 * 
 * @author Dustin Singleton
 */
@SuppressWarnings({ "javadoc", "nls" })
public class SetImmutabilityTest
{
    private final ProxyBuilderFactory factory = new ProxyBuilderFactory();
    private final Builder builder = factory.createBuilderForClass(Builder.class);

    @Test
    public void testBuilderImmutability_withImmutableAnnotation()
    {
        Set<String> originalSet = new HashSet<String>(Arrays.asList("String1", "String2"));
        Set<String> setToModify = new HashSet<String>(originalSet);

        BuiltObject builtObject = builder.immutableSetValue(setToModify).build();
        Iterator<String> iterator = setToModify.iterator();
        iterator.next();
        iterator.remove();

        assertThat(builtObject.getImmutableSetValue(), is(originalSet));
        assertThat(builtObject.getImmutableSetValue().size(), is(2));
        assertThat(builtObject.getImmutableSetValue(), is(not(sameInstance(originalSet))));

        Iterator<String> builtObjectIterator = builtObject.getImmutableSetValue().iterator();
        builtObjectIterator.next();
        builtObjectIterator.remove();

        assertThat(builtObject.getImmutableSetValue(), is(originalSet));
        assertThat(builtObject.getImmutableSetValue().size(), is(2));
    }

    @Test
    public void testBuilderImmutability_nullSetWithImmutableAnnotation()
    {
        BuiltObject builtObject = builder.immutableSetValue(null).build();

        assertThat(builtObject.getImmutableSetValue(), is(nullValue()));
    }

    @Test
    public void testBuilderImmutability_withoutImmutableAnnotation()
    {
        Set<String> originalSet = new HashSet<String>(Arrays.asList("String1", "String2"));
        Set<String> setToModify = new HashSet<String>(originalSet);

        BuiltObject builtObject = builder.mutableSetValue(setToModify).build();
        Iterator<String> iterator = setToModify.iterator();
        iterator.next();
        iterator.remove();

        assertThat(builtObject.getMutableSetValue(), is(setToModify));
        assertThat(builtObject.getMutableSetValue().size(), is(1));
        assertThat(builtObject.getMutableSetValue(), is(sameInstance(setToModify)));

        Iterator<String> builtObjectIterator = builtObject.getMutableSetValue().iterator();
        builtObjectIterator.next();
        builtObjectIterator.remove();

        assertThat(builtObject.getMutableSetValue(), is(setToModify));
        assertThat(builtObject.getMutableSetValue().size(), is(0));
    }

    @Test
    public void testBuilderImmutability_nullSetWithoutImmutableAnnotation()
    {
        BuiltObject builtObject = builder.mutableSetValue(null).build();

        assertThat(builtObject.getMutableSetValue(), is(nullValue()));
    }

    private interface Builder
    {
        Builder mutableSetValue(Set<String> set);

        Builder immutableSetValue(@Immutable Set<String> set);

        BuiltObject build();
    }

    private interface BuiltObject
    {
        Set<String> getMutableSetValue();

        Set<String> getImmutableSetValue();
    }
}
