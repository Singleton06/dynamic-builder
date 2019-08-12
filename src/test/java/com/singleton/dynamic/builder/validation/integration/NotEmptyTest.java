package com.singleton.dynamic.builder.validation.integration;

import static com.singleton.dynamic.builder.validation.NotParameterValidator.EMPTY;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.singleton.dynamic.builder.DynamicBuilderFactory;
import com.singleton.dynamic.builder.annotation.Not;

/**
 * <p>
 * Test to ensure that the not empty annotation works.
 * </p>
 *
 * @author Dustin Singleton
 */
@SuppressWarnings({ "javadoc", "nls" })
public class NotEmptyTest
{
    private final DynamicBuilderFactory factory = DynamicBuilderFactory.getInstance();

    @Test
    public void testNotNull_nullStringValueAllowed()
    {
        NotEmptyStringBuilder builder = factory.createBuilderForClass(NotEmptyStringBuilder.class);

        NotEmptyStringBuiltObject builtObject = builder.stringValue(null).build();
        assertThat(builtObject.getStringValue(), is((String) null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotAnnotation_emptyStringValueNotAllowed()
    {
        factory.createBuilderForClass(NotEmptyStringBuilder.class).stringValue("");
    }

    @Test
    public void testNotAnnotation_nullCollectionValueAllowed()
    {
        NotEmptyCollectionBuilder builder = factory.createBuilderForClass(NotEmptyCollectionBuilder.class)
                .collectionValue(null);

        assertThat(builder.build().getCollectionValue(), is(nullValue()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotAnnoitation_emptyCollectionValueNotAllowed()
    {
        factory.createBuilderForClass(NotEmptyCollectionBuilder.class).collectionValue(Collections.<Long> emptyList());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotAnnotation_emptyListValueNotAllowed()
    {
        List<Long> emptyList = new ArrayList<Long>();

        factory.createBuilderForClass(NotEmptyListBuilder.class).listValue(emptyList);
    }

    @Test
    public void testNotAnnotation_nonEmptyListAllowed()
    {
        List<Long> list = Arrays.asList(1L);

        NotEmptyListBuilder builder = factory.createBuilderForClass(NotEmptyListBuilder.class);
        NotEmptyListObject object = builder.listValue(list).build();

        assertThat(object.getListValue(), is(list));
    }

    @Test
    public void testNotAnnotation_nullMapValueAllowed()
    {
        Map<Long, Long> map = null;

        NotEmptyMapBuilder builder = factory.createBuilderForClass(NotEmptyMapBuilder.class);
        NotEmptyMapObject object = builder.mapValue(map).build();

        assertThat(object.getMapValue(), is(nullValue()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotAnnotation_emptyMapNotAllowed()
    {
        factory.createBuilderForClass(NotEmptyMapBuilder.class).mapValue(Collections.<Long, Long> emptyMap());
    }

    @Test
    public void testNotAnnotation_nonEmptyMapAllowed()
    {
        Map<Long, Long> map = new HashMap<Long, Long>();
        map.put(1L, 2L);

        NotEmptyMapBuilder builder = factory.createBuilderForClass(NotEmptyMapBuilder.class);
        NotEmptyMapObject object = builder.mapValue(map).build();

        assertThat(object.getMapValue(), is(map));
    }

    public interface NotEmptyListBuilder
    {
        NotEmptyListBuilder listValue(@Not({ EMPTY }) List<Long> value);

        NotEmptyListObject build();
    }

    public interface NotEmptyListObject
    {
        List<Long> getListValue();
    }

    public interface NotEmptyCollectionBuilder
    {
        NotEmptyCollectionBuilder collectionValue(@Not({ EMPTY }) Collection<Long> value);

        NotEmptyCollectionObject build();
    }

    public interface NotEmptyCollectionObject
    {
        Collection<Long> getCollectionValue();
    }

    public interface NotEmptyMapBuilder
    {
        NotEmptyMapBuilder mapValue(@Not({ EMPTY }) Map<Long, Long> value);

        NotEmptyMapObject build();
    }

    public interface NotEmptyMapObject
    {
        Map<Long, Long> getMapValue();
    }

    public interface NotEmptyStringBuilder
    {
        NotEmptyStringBuilder stringValue(@Not({ EMPTY }) String value);

        NotEmptyStringBuiltObject build();
    }

    public interface NotEmptyStringBuiltObject
    {
        String getStringValue();
    }
}