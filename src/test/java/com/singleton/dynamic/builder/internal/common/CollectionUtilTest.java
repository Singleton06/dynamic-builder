package com.singleton.dynamic.builder.internal.common;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collection;

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
    public void testCopyCollections_sameValues()
    {
        String value1 = "value1";
        String value2 = "value2";
        Collection<String> originalCollection = Arrays.asList(value1, value2);

        Collection<String> copiedCollection = CollectionUtil.copyCollection(originalCollection);

        assertThat(copiedCollection.size(), is(originalCollection.size()));
        assertThat(copiedCollection, is(originalCollection));
    }

    @Test
    public void testCopyCollections_emptyValues()
    {
        assertThat(CollectionUtil.copyCollection(null).size(), is(0));
    }
}