package com.singleton.dynamic.builder.proxy.supportedtypes;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.singleton.dynamic.builder.proxy.ProxyBuilderFactory;

/**
 * Test that ensures that the dynamic builder supports generics.
 *
 * @author Dustin Singleton
 */
@SuppressWarnings({ "javadoc", "nls" })
public class GenericsTest
{
    private final ProxyBuilderFactory factory = new ProxyBuilderFactory();

    @Test
    public void testList()
    {
        List<String> stringList = new ArrayList<String>();
        stringList.add("a");
        stringList.add("b");

        ListBuilder builder = factory.createBuilderForClass(ListBuilder.class);
        builder.stringList(stringList);

        assertThat(builder.build().getStringList(), is(stringList));
    }

    @Test
    public void testGenericObject()
    {
        @SuppressWarnings("unchecked")
        GenericBuilder<String> builder = factory.createBuilderForClass(GenericBuilder.class);

        GenericBuiltObject<String> builtObject = builder.value("string").build();

        assertThat(builtObject.getValue(), is("string"));
    }

    /**
     * This test shows that if you want a more elegant solution so that you do not have to cast, you can always extend
     * the interface to provide the generic that you would like and all of the methods will still be inherited.
     */
    @Test
    public void testGenericObject_extendedInterface()
    {
        GenericBuilder<String> builder = factory.createBuilderForClass(StringGenericBuilder.class);

        GenericBuiltObject<String> builtObject = builder.value("string").build();

        assertThat(builtObject.getValue(), is("string"));
    }

    private interface StringGenericBuilder extends GenericBuilder<String>
    {
    }

    private interface GenericBuilder<T>
    {
        GenericBuilder<T> value(T value);

        GenericBuiltObject<T> build();
    }

    private interface GenericBuiltObject<T>
    {
        T getValue();
    }

    private interface ListBuilder
    {
        ListBuilder stringList(List<String> stringList);

        ListBuiltObject build();
    }

    private interface ListBuiltObject
    {
        List<String> getStringList();
    }
}