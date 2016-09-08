package com.singleton.dynamic.builder.internal.common;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

/**
 * Test class for {@link CollectionUtil}.
 * 
 * @author Dustin Singleton
 */
@SuppressWarnings({ "javadoc" })
public class CollectionUtilTest
{
    @Test
    public void testEnum_valueOf()
    {
        assertThat(CollectionUtil.valueOf("INSTANCE"), is(CollectionUtil.INSTANCE));
    }

    @Test
    public void testEnum_values()
    {
        CollectionUtil[] values = CollectionUtil.values();

        assertThat(values.length, is(1));
        assertThat(values[0], is(CollectionUtil.INSTANCE));
    }

    @Test
    public void testCopyCollections_sameValues()
    {
        String value1 = "value1";
        String value2 = "value2";
        Collection<String> originalCollection = Arrays.asList(value1, value2);

        Collection<String> copiedCollection = CollectionUtil.INSTANCE.copyCollection(originalCollection);

        assertThat(copiedCollection.size(), is(originalCollection.size()));
        assertThat(copiedCollection, is(originalCollection));
    }

    @Test
    public void testCopyCollections_emptyValues()
    {
        assertThat(CollectionUtil.INSTANCE.copyCollection(null).size(), is(0));
    }

    @Test
    public void testCopyLists_sameValues()
    {
        String value1 = "value1";
        String value2 = "value2";
        List<String> originalList = Arrays.asList(value1, value2);

        List<String> copiedList = CollectionUtil.INSTANCE.copyList(originalList);

        assertThat(copiedList.size(), is(originalList.size()));
        assertThat(copiedList, is(originalList));
    }

    @Test
    public void testCopyLists_emptyValues()
    {
        assertThat(CollectionUtil.INSTANCE.copyList(null).size(), is(0));
    }

    @Test
    public void testCopySets_sameValues()
    {
        String value1 = "value1";
        String value2 = "value2";
        Set<String> originalSet = new HashSet<String>();
        originalSet.add(value1);
        originalSet.add(value2);

        Set<String> copiedSet = CollectionUtil.INSTANCE.copySet(originalSet);

        assertThat(copiedSet.size(), is(originalSet.size()));
        assertThat(copiedSet, is(originalSet));
    }

    @Test
    public void testCopySets_emptyValues()
    {
        assertThat(CollectionUtil.INSTANCE.copySet(null).size(), is(0));
    }
}