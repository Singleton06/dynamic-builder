package com.singleton.dynamic.builder.integration.defaults;

import static com.singleton.dynamic.builder.defaults.DefaultValueType.EMPTY;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

import com.singleton.dynamic.builder.DynamicBuilderFactory;
import com.singleton.dynamic.builder.annotation.DefaultValue;

/**
 * Test to validate that if builder methods of parameter type {@link Collection} is not called then specified default
 * value is used.
 *
 * @author Prateek Kansal
 */
@SuppressWarnings("javadoc")
public class DefaultValueAnnotationCollectionTest
{
    @Test
    public void testDefaultValueAnnotation_forEmptyCollection()
    {
        assertEquals(new ArrayList(),
                new DynamicBuilderFactory().createBuilderForClass(DefaultValueCollectionObjectBuilder.class).intValue(1)
                        .build().getCollectionValue());
    }

    private interface DefaultValueCollectionObjectBuilder
    {
        DefaultValueCollectionObjectBuilder collectionValue(@DefaultValue(EMPTY) Collection<?> value);

        DefaultValueCollectionObjectBuilder intValue(int value);

        DefaultValueCollectionObject build();
    }

    private interface DefaultValueCollectionObject

    {
        Collection<?> getCollectionValue();

        int getIntValue();
    }
}
