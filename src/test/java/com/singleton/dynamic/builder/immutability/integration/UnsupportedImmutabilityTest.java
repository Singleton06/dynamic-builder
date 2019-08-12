package com.singleton.dynamic.builder.immutability.integration;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.singleton.dynamic.builder.DynamicBuilderFactory;
import com.singleton.dynamic.builder.annotation.Immutable;

/**
 * Test class to ensure that the dynamic builder will just do a pass through when an unsupported type is specified to be
 * immutable.
 * 
 * @author Dustin Singleton
 */
@SuppressWarnings({ "javadoc" })
public class UnsupportedImmutabilityTest
{
    private final DynamicBuilderFactory factory = DynamicBuilderFactory.getInstance();
    private final Builder builder = factory.createBuilderForClass(Builder.class);

    @Test
    public void testBuilder_stringValueWithImmutableAnnotationJustDoesPassthrough()
    {
        assertThat(builder.immutableStringValue("testing").build().getImmutableStringValue(), is("testing"));
    }

    public interface Builder
    {
        Builder immutableStringValue(@Immutable String string);

        BuiltObject build();
    }

    public interface BuiltObject
    {
        String getImmutableStringValue();
    }
}
