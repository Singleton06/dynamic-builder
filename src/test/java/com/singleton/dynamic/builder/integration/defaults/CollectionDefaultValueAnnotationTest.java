package com.singleton.dynamic.builder.integration.defaults;

import static com.singleton.dynamic.builder.defaults.DefaultValueType.EMPTY;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

import com.singleton.dynamic.builder.DynamicBuilderFactory;
import com.singleton.dynamic.builder.annotation.DefaultValue;

/**
 * Test to validate the default value annotation on the builder method parameter of type {@link Collection}.
 *
 * @author Prateek Kansal
 */
@SuppressWarnings("javadoc")
public class CollectionDefaultValueAnnotationTest
{
    @Test
    public void testDefaultValueAnnotation_forEmptyCollection()
    {
        assertEquals(new ArrayList(),
                new DynamicBuilderFactory().createBuilderForClass(DefaultValueCollectionObjectBuilder.class)
                        .collectionValue(null).build()
                        .getCollectionValue());
    }

    private interface DefaultValueCollectionObjectBuilder
    {
        DefaultValueCollectionObjectBuilder collectionValue(@DefaultValue(EMPTY) Collection<?> value);

        DefaultValueCollectionObject build();
    }
    
    private interface DefaultValueCollectionObject

    {
        Collection<?> getCollectionValue();
    }
}
