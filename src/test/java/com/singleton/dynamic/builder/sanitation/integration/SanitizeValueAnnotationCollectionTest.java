package com.singleton.dynamic.builder.sanitation.integration;

import static com.singleton.dynamic.builder.sanitation.SanitizeValueType.EMPTY;
import static com.singleton.dynamic.builder.sanitation.SanitizeValueType.IGNORE_NULL_ELEMENTS;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

import com.singleton.dynamic.builder.annotation.SanitizeValue;
import com.singleton.dynamic.builder.proxy.ProxyBuilderFactory;

/**
 * Test to validate the sanitize annotation for the builder method parameter of type {@link Collection}.
 *
 * @author Prateek Kansal
 */
@SuppressWarnings({ "javadoc", "nls" })
public class SanitizeValueAnnotationCollectionTest
{
    @Test
    public void testSanitizeCollection_EmptyValue()
    {
        assertEquals(new ArrayList<Object>(),
                new ProxyBuilderFactory().createBuilderForClass(SantizeCollectionEmptyObjectBuilder.class)
                        .collectionValue(null).build().getCollectionValue());
    }

    @Test
    public void testSanitizeCollection_IgnoreNullElements()
    {
        Collection<String> inputCollectionValue = new ArrayList<String>();
        inputCollectionValue.add(null);
        inputCollectionValue.add("test-string");
        inputCollectionValue.add(null);

        assertEquals(inputCollectionValue.size(), 3);
        assertEquals(1,
                new ProxyBuilderFactory().createBuilderForClass(SantizeCollectionNullElementsObjectBuilder.class)
                        .collectionValue(inputCollectionValue).build().getCollectionValue().size());
    }

    private interface SantizeCollectionEmptyObjectBuilder
    {
        SantizeCollectionEmptyObjectBuilder collectionValue(@SanitizeValue(EMPTY) Collection<?> value);

        SantizeCollectionEmptyObject build();
    }

    private interface SantizeCollectionEmptyObject

    {
        Collection<?> getCollectionValue();
    }

    private interface SantizeCollectionNullElementsObjectBuilder
    {
        SantizeCollectionNullElementsObjectBuilder collectionValue(
                @SanitizeValue(IGNORE_NULL_ELEMENTS) Collection<?> value);

        SantizeCollectionNullElementsObject build();
    }

    private interface SantizeCollectionNullElementsObject
    {
        Collection<?> getCollectionValue();
    }
}
