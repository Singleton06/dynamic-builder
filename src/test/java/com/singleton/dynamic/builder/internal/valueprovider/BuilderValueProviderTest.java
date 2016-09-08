package com.singleton.dynamic.builder.internal.valueprovider;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.junit.Test;

import com.singleton.dynamic.builder.annotation.Immutable;

/**
 * Test class for {@link BuilderValueProvider}.
 * 
 * @author Dustin Singleton
 */
@SuppressWarnings({ "javadoc" })
public class BuilderValueProviderTest
{
    private final BuilderValueProvider valueProvider = new BuilderValueProvider();

    private final String mutableDateValueMethodName = "mutableDateValue";
    private final Method mutableDateMethod = getMethod(mutableDateValueMethodName, Date.class);

    private final String immutableDateValueMethodName = "immutableDateValue";
    private final Method immutableDateMethod = getMethod(immutableDateValueMethodName, Date.class);

    private final String mutableCollectionValueMethodName = "mutableCollectionValue";
    private final Method mutableCollectionValueMethod = getMethod(mutableCollectionValueMethodName, Collection.class);

    private final String immutableCollectionValueMethodName = "immutableCollectionValue";
    private final Method immutableCollectionValueMethod = getMethod(immutableCollectionValueMethodName,
            Collection.class);

    private static Method getMethod(String name, Class<?>... parameterTypes)
    {
        try
        {
            return TestBuilderInterface.class.getMethod(name, parameterTypes);
        }
        catch (Throwable t)
        {
            throw new RuntimeException("Exception while trying to get method name: " + name, t);
        }
    }

    @Test
    public void testGetValue_mutableDate()
    {
        long originalTime = 1000L;
        long modifyTime = 1001L;
        Date dateToBeModified = new Date(originalTime);

        Object value = valueProvider.getValue(mutableDateMethod, dateToBeModified);
        dateToBeModified.setTime(modifyTime);

        assertThat(value, is(instanceOf(Date.class)));
        assertThat(((Date) value).getTime(), is(modifyTime));
    }

    @Test
    public void testGetValue_immutableDate()
    {
        long originalTime = 1000L;
        long modifyTime = 1001L;
        Date dateToBeModified = new Date(originalTime);

        Object value = valueProvider.getValue(immutableDateMethod, dateToBeModified);
        dateToBeModified.setTime(modifyTime);

        assertThat(value, is(instanceOf(Date.class)));
        assertThat(((Date) value).getTime(), is(originalTime));
    }

    @Test
    public void testGetValue_nullImmutableDate()
    {
        Object value = valueProvider.getValue(immutableDateMethod, null);

        assertThat(value, is(nullValue()));
    }

    // Suppression added because of the lack of type safety that the handlers have
    @SuppressWarnings("unchecked")
    @Test
    public void testGetValue_immutableCollection()
    {
        Collection<String> originalCollection = Arrays.asList("String1", "String2");
        Collection<String> collectionToModify = new ArrayList<String>(originalCollection);

        Object value = valueProvider.getValue(immutableCollectionValueMethod, collectionToModify);
        Iterator<String> iterator = collectionToModify.iterator();
        iterator.next();
        iterator.remove();

        assertThat((Collection<String>) value, is(originalCollection));
    }

    @Test
    public void testGetValue_nullImmutableCollection()
    {
        Object value = valueProvider.getValue(immutableCollectionValueMethod, null);

        assertThat(value, is(nullValue()));
    }

    // Suppression added because of the lack of type safety that the handlers have
    @SuppressWarnings("unchecked")
    @Test
    public void testGetValue_mutableCollection()
    {
        Collection<String> originalCollection = Arrays.asList("String1", "String2");
        Collection<String> collectionToModify = new ArrayList<String>(originalCollection);

        Object value = valueProvider.getValue(mutableCollectionValueMethod, collectionToModify);
        Iterator<String> iterator = collectionToModify.iterator();
        iterator.next();
        iterator.remove();

        assertThat((Collection<String>) value, is(collectionToModify));
    }

    private interface TestBuilderInterface
    {
        TestBuilderInterface mutableDateValue(Date date);

        TestBuilderInterface immutableDateValue(@Immutable Date date);

        TestBuilderInterface mutableCollectionValue(Collection<String> collection);

        TestBuilderInterface immutableCollectionValue(@Immutable Collection<String> collection);
    }
}
