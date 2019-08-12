package com.singleton.dynamic.builder.defaults.integration;

import static com.singleton.dynamic.builder.defaults.DefaultValueType.EMPTY;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

import com.singleton.dynamic.builder.DynamicBuilderFactory;
import com.singleton.dynamic.builder.annotation.DefaultValue;

/**
 * Test to validate that if builder methods of parameter type {@link Collection}
 * is not called then specified default value is used.
 *
 * @author Prateek Kansal
 */
@SuppressWarnings("javadoc")
public class DefaultValueAnnotationCollectionTest {
    @Test
    public void testDefaultValueAnnotation_forEmptyCollection() {
        assertEquals(new ArrayList<Object>(),
                DynamicBuilderFactory.getInstance().createBuilderForClass(DefaultValueCollectionObjectBuilder.class)
                        .intValue(1).build().getCollectionValue());
    }

    public interface DefaultValueCollectionObjectBuilder {
        DefaultValueCollectionObjectBuilder collectionValue(@DefaultValue(EMPTY) Collection<?> value);

        DefaultValueCollectionObjectBuilder intValue(int value);

        DefaultValueCollectionObject build();
    }

    public interface DefaultValueCollectionObject
    {
        Collection<?> getCollectionValue();

        int getIntValue();
    }
}
