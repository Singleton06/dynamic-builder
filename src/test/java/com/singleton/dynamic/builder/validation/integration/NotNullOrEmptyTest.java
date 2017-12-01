package com.singleton.dynamic.builder.validation.integration;

import static com.singleton.dynamic.builder.validation.NotParameterValidator.EMPTY;
import static com.singleton.dynamic.builder.validation.NotParameterValidator.NULL;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.singleton.dynamic.builder.annotation.Not;
import com.singleton.dynamic.builder.proxy.ProxyBuilderFactory;

/**
 * <p>
 * Test class to ensure that {@link Not} works with empty and {@code null}.
 * </p>
 *
 * @author Dustin Singleton
 */
@SuppressWarnings({ "javadoc", "nls" })
public class NotNullOrEmptyTest
{
    private final ProxyBuilderFactory factory = new ProxyBuilderFactory();

    @Test(expected = IllegalArgumentException.class)
    public void testNotEmptyNotNull_nullStringNotAllowed()
    {
        factory.createBuilderForClass(NotNullOrEmptStringBuilder.class).stringValue(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotEmptyNotNull_emptyStringNotAllowed()
    {
        factory.createBuilderForClass(NotNullOrEmptStringBuilder.class).stringValue("");
    }

    @Test
    public void testNotEmptyNotNull_stringWithValueAllowed()
    {
        NotNullOrEmptStringBuilder builder = factory.createBuilderForClass(NotNullOrEmptStringBuilder.class);

        assertThat(builder.stringValue("value").build().getStringValue(), is("value"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotEmptyNotNull_nullCollectionNotAllowed()
    {
        factory.createBuilderForClass(NotNullOrEmptyCollectionBuilder.class).collectionValue(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotEmptyNotNull_emptyCollectionNotAllowed()
    {
        factory.createBuilderForClass(NotNullOrEmptyCollectionBuilder.class)
                .collectionValue(Collections.<Long> emptyList());
    }

    @Test
    public void testNotEmptyNotNull_collectionWithValuesAllowed()
    {
        Collection<Long> expectedValue = Collections.singleton(1L);
        NotNullOrEmptyCollectionBuilder builder = factory.createBuilderForClass(NotNullOrEmptyCollectionBuilder.class);

        Collection<Long> actualValue = builder.collectionValue(Collections.singleton(1L)).build().getCollectionValue();

        assertThat(actualValue, is(expectedValue));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotEmptyNotNull_nullMapNotAllowed()
    {
        factory.createBuilderForClass(NotNullOrEmptyMapBuilder.class).mapValue(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotEmptyNotNull_emptyMapNotAllowed()
    {
        factory.createBuilderForClass(NotNullOrEmptyMapBuilder.class).mapValue(Collections.<Long, Long> emptyMap());
    }

    @Test
    public void testNotEmptyNotNull_mapWithValuesAllowed()
    {
        Map<Long, Long> map = new HashMap<Long, Long>();
        map.put(1L, 2L);

        NotNullOrEmptyMapBuilder builder = factory.createBuilderForClass(NotNullOrEmptyMapBuilder.class);
        Map<Long, Long> actualValue = builder.mapValue(map).build().getMapValue();

        assertThat(actualValue, is(map));
    }

    private interface NotNullOrEmptyCollectionBuilder
    {
        NotNullOrEmptyCollectionBuilder collectionValue(@Not({ NULL, EMPTY }) Collection<Long> collectionValue);

        NotNullOrEmptyCollectionBuiltObject build();
    }

    private interface NotNullOrEmptyCollectionBuiltObject
    {
        Collection<Long> getCollectionValue();
    }

    private interface NotNullOrEmptyMapBuilder
    {
        NotNullOrEmptyMapBuilder mapValue(@Not({ NULL, EMPTY }) Map<Long, Long> mapValue);

        NotNullOrEmptyMapBuiltObject build();
    }

    private interface NotNullOrEmptyMapBuiltObject
    {
        Map<Long, Long> getMapValue();
    }

    private interface NotNullOrEmptStringBuilder
    {
        NotNullOrEmptStringBuilder stringValue(@Not({ NULL, EMPTY }) String value);

        NotNullOrEmptyStringBuiltObject build();
    }

    private interface NotNullOrEmptyStringBuiltObject
    {
        String getStringValue();
    }
}
