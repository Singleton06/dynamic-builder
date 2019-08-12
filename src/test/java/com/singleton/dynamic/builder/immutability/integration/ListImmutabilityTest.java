package com.singleton.dynamic.builder.immutability.integration;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.singleton.dynamic.builder.DynamicBuilderFactory;
import com.singleton.dynamic.builder.annotation.Immutable;

/**
 * Test to ensure that the {@link Immutable} annotation functions as expected.
 * 
 * @author Dustin Singleton
 */
@SuppressWarnings({ "javadoc", "nls" })
public class ListImmutabilityTest
{
    private final DynamicBuilderFactory factory = DynamicBuilderFactory.getInstance();
    private final Builder builder = factory.createBuilderForClass(Builder.class);

    @Test
    public void testBuilderImmutability_withImmutableAnnotation()
    {
        List<String> originalList = Arrays.asList("String1", "String2");
        List<String> listToModify = new ArrayList<String>(originalList);

        BuiltObject builtObject = builder.immutableListValue(listToModify).build();

        listToModify.remove(0);
        assertThat(builtObject.getImmutableListValue(), is(originalList));
        assertThat(builtObject.getImmutableListValue().size(), is(2));
        assertThat(builtObject.getImmutableListValue(), is(not(sameInstance(originalList))));

        builtObject.getImmutableListValue().remove(0);
        assertThat(builtObject.getImmutableListValue(), is(originalList));
        assertThat(builtObject.getImmutableListValue().size(), is(2));
    }

    @Test
    public void testBuilderImmutability_nullListWithImmutableAnnotation()
    {
        BuiltObject builtObject = builder.immutableListValue(null).build();

        assertThat(builtObject.getImmutableListValue(), is(nullValue()));
    }

    @Test
    public void testBuilderImmutability_withoutImmutableAnnotation()
    {
        List<String> originalList = Arrays.asList("String1", "String2");
        List<String> listToModify = new ArrayList<String>(originalList);

        BuiltObject builtObject = builder.mutableListValue(listToModify).build();
        listToModify.remove(0);

        assertThat(builtObject.getMutableListValue(), is(listToModify));
        assertThat(builtObject.getMutableListValue().size(), is(1));
        assertThat(builtObject.getMutableListValue(), is(sameInstance(listToModify)));

        builtObject.getMutableListValue().remove(0);
        assertThat(builtObject.getMutableListValue(), is(listToModify));
        assertThat(builtObject.getMutableListValue().size(), is(0));
    }

    @Test
    public void testBuilderImmutability_nullListWithoutImmutableAnnotation()
    {
        BuiltObject builtObject = builder.mutableListValue(null).build();

        assertThat(builtObject.getMutableListValue(), is(nullValue()));
    }

    public interface Builder
    {
        Builder mutableListValue(List<String> list);

        Builder immutableListValue(@Immutable List<String> list);

        BuiltObject build();
    }

    public interface BuiltObject
    {
        List<String> getMutableListValue();

        List<String> getImmutableListValue();
    }
}
