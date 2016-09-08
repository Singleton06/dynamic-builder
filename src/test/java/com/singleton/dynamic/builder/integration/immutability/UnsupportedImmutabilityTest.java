package com.singleton.dynamic.builder.integration.immutability;

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
    private final DynamicBuilderFactory factory = new DynamicBuilderFactory();
    private final Builder builder = factory.createBuilderForClass(Builder.class);

    @Test
    public void testBuilder_stringValueWithImmutableAnnotationJustDoesPassthrough()
    {
        assertThat(builder.immutableStringValue("testing").build().getImmutableStringValue(), is("testing"));
    }

    private interface Builder
    {
        Builder immutableStringValue(@Immutable String string);

        BuiltObject build();
    }

    private interface BuiltObject
    {
        String getImmutableStringValue();
    }
}
