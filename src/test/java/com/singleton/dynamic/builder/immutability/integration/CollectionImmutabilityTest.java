package com.singleton.dynamic.builder.immutability.integration;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import org.junit.Test;

import com.singleton.dynamic.builder.annotation.Immutable;
import com.singleton.dynamic.builder.proxy.ProxyBuilderFactory;

/**
 * Test to ensure that the {@link Immutable} annotation functions as expected.
 * 
 * @author Dustin Singleton
 */
@SuppressWarnings({ "javadoc", "nls" })
public class CollectionImmutabilityTest
{
    private final ProxyBuilderFactory factory = new ProxyBuilderFactory();
    private final Builder builder = factory.createBuilderForClass(Builder.class);

    @Test
    public void testBuilderImmutability_withImmutableAnnotation()
    {
        Collection<String> originalCollection = Arrays.asList("String1", "String2");
        Collection<String> collectionToModify = new ArrayList<String>(originalCollection);

        BuiltObject builtObject = builder.immutableCollectionValue(collectionToModify).build();
        Iterator<String> iterator = collectionToModify.iterator();
        iterator.next();
        iterator.remove();

        assertThat(builtObject.getImmutableCollectionValue(), is(originalCollection));
        assertThat(builtObject.getImmutableCollectionValue().size(), is(2));
        assertThat(builtObject.getImmutableCollectionValue(), is(not(sameInstance(originalCollection))));

        Iterator<String> builtObjectIterator = builtObject.getImmutableCollectionValue().iterator();
        builtObjectIterator.next();
        builtObjectIterator.remove();

        assertThat(builtObject.getImmutableCollectionValue(), is(originalCollection));
        assertThat(builtObject.getImmutableCollectionValue().size(), is(2));
    }

    @Test
    public void testBuilderImmutability_nullCollectionWithImmutableAnnotation()
    {
        BuiltObject builtObject = builder.immutableCollectionValue(null).build();

        assertThat(builtObject.getImmutableCollectionValue(), is(nullValue()));
    }

    @Test
    public void testBuilderImmutability_withoutImmutableAnnotation()
    {
        Collection<String> originalCollection = Arrays.asList("String1", "String2");
        Collection<String> collectionToModify = new ArrayList<String>(originalCollection);

        BuiltObject builtObject = builder.mutableCollectionValue(collectionToModify).build();
        Iterator<String> iterator = collectionToModify.iterator();
        iterator.next();
        iterator.remove();

        assertThat(builtObject.getMutableCollectionValue(), is(collectionToModify));
        assertThat(builtObject.getMutableCollectionValue().size(), is(1));
        assertThat(builtObject.getMutableCollectionValue(), is(sameInstance(collectionToModify)));

        Iterator<String> builtObjectIterator = builtObject.getMutableCollectionValue().iterator();
        builtObjectIterator.next();
        builtObjectIterator.remove();

        assertThat(builtObject.getMutableCollectionValue(), is(collectionToModify));
        assertThat(builtObject.getMutableCollectionValue().size(), is(0));
    }

    @Test
    public void testBuilderImmutability_nullCollectionWithoutImmutableAnnotation()
    {
        BuiltObject builtObject = builder.mutableCollectionValue(null).build();

        assertThat(builtObject.getMutableCollectionValue(), is(nullValue()));
    }

    private interface Builder
    {
        Builder mutableCollectionValue(Collection<String> collection);

        Builder immutableCollectionValue(@Immutable Collection<String> collection);

        BuiltObject build();
    }

    private interface BuiltObject
    {
        Collection<String> getMutableCollectionValue();

        Collection<String> getImmutableCollectionValue();
    }
}
