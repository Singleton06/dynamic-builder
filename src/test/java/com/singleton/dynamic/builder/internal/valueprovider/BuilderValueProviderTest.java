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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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

    private final String mutableListValueMethodName = "mutableListValue";
    private final Method mutableListValueMethod = getMethod(mutableListValueMethodName, List.class);

    private final String immutableListValueMethodName = "immutableListValue";
    private final Method immutableListValueMethod = getMethod(immutableListValueMethodName, List.class);

    private final String mutableSetValueMethodName = "mutableSetValue";
    private final Method mutableSetValueMethod = getMethod(mutableSetValueMethodName, Set.class);

    private final String immutableSetValueMethodName = "immutableSetValue";
    private final Method immutableSetValueMethod = getMethod(immutableSetValueMethodName, Set.class);

    private final String immutableStringValueMethodName = "immutableStringValue";
    private final Method immutableStringValueMethod = getMethod(immutableStringValueMethodName, String.class);

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
    public void testGetValue_nullMutableDate()
    {
        Object value = valueProvider.getValue(mutableDateMethod, null);

        assertThat(value, is(nullValue()));
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

    @Test
    public void testGetValue_nullMutableCollection()
    {
        Object value = valueProvider.getValue(mutableCollectionValueMethod, null);

        assertThat(value, is(nullValue()));
    }

    // Suppression added because of the lack of type safety that the handlers have
    @SuppressWarnings("unchecked")
    @Test
    public void testGetValue_immutableList()
    {
        List<String> originalList = Arrays.asList("String1", "String2");
        List<String> ListToModify = new ArrayList<String>(originalList);

        Object value = valueProvider.getValue(immutableListValueMethod, ListToModify);
        Iterator<String> iterator = ListToModify.iterator();
        iterator.next();
        iterator.remove();

        assertThat((List<String>) value, is(originalList));
    }

    @Test
    public void testGetValue_nullImmutableList()
    {
        Object value = valueProvider.getValue(immutableListValueMethod, null);

        assertThat(value, is(nullValue()));
    }

    // Suppression added because of the lack of type safety that the handlers have
    @SuppressWarnings("unchecked")
    @Test
    public void testGetValue_mutableList()
    {
        List<String> originalList = Arrays.asList("String1", "String2");
        List<String> ListToModify = new ArrayList<String>(originalList);

        Object value = valueProvider.getValue(mutableListValueMethod, ListToModify);
        Iterator<String> iterator = ListToModify.iterator();
        iterator.next();
        iterator.remove();

        assertThat((List<String>) value, is(ListToModify));
    }

    @Test
    public void testGetValue_nullMutableList()
    {
        Object value = valueProvider.getValue(mutableListValueMethod, null);

        assertThat(value, is(nullValue()));
    }

    // Suppression added because of the lack of type safety that the handlers have
    @SuppressWarnings("unchecked")
    @Test
    public void testGetValue_immutableSet()
    {
        Set<String> originalSet = new HashSet<String>(Arrays.asList("String1", "String2"));
        Set<String> setToModify = new HashSet<String>(originalSet);

        Object value = valueProvider.getValue(immutableSetValueMethod, setToModify);
        Iterator<String> iterator = setToModify.iterator();
        iterator.next();
        iterator.remove();

        assertThat((Set<String>) value, is(originalSet));
    }

    @Test
    public void testGetValue_nullImmutableSet()
    {
        Object value = valueProvider.getValue(immutableSetValueMethod, null);

        assertThat(value, is(nullValue()));
    }

    // Suppression added because of the lack of type safety that the handlers have
    @SuppressWarnings("unchecked")
    @Test
    public void testGetValue_mutableSet()
    {
        Set<String> originalSet = new HashSet<String>(Arrays.asList("String1", "String2"));
        Set<String> setToModify = new HashSet<String>(originalSet);

        Object value = valueProvider.getValue(mutableSetValueMethod, setToModify);
        Iterator<String> iterator = setToModify.iterator();
        iterator.next();
        iterator.remove();

        assertThat((Set<String>) value, is(setToModify));
    }

    @Test
    public void testGetValue_nullMutableSet()
    {
        Object value = valueProvider.getValue(mutableSetValueMethod, null);

        assertThat(value, is(nullValue()));
    }

    /**
     * This test is to ensure that in situations where the type that is declared to be immutable is not technically
     * supported, that the argument is just returned in that situation.
     */
    @Test
    public void testGetValue_stringValueWithImmutableAnnotation()
    {
        String value = (String) valueProvider.getValue(immutableStringValueMethod, "testing");

        assertThat(value, is("testing"));
    }

    private interface TestBuilderInterface
    {
        TestBuilderInterface mutableDateValue(Date date);

        TestBuilderInterface immutableDateValue(@Immutable Date date);

        TestBuilderInterface mutableCollectionValue(Collection<String> collection);

        TestBuilderInterface immutableCollectionValue(@Immutable Collection<String> collection);

        TestBuilderInterface mutableListValue(List<String> list);

        TestBuilderInterface immutableListValue(@Immutable List<String> list);

        TestBuilderInterface mutableSetValue(Set<String> set);

        TestBuilderInterface immutableSetValue(@Immutable Set<String> set);

        TestBuilderInterface immutableStringValue(@Immutable String string);
    }
}
