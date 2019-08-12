package com.singleton.dynamic.builder.internal.inspection;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Type;

import org.junit.Test;

import com.singleton.dynamic.builder.internal.representation.BuilderModel;
import com.singleton.dynamic.builder.internal.representation.BuilderProperty;

/**
 * <p>
 * Tests for {@link AnnotationInspector}.
 * </p>
 *
 * @author Brandon Callison
 */
@SuppressWarnings({ "javadoc", "nls", "rawtypes" })
public class AnnotationInspectorTest
{
    private static final AnnotationInspector inspector = new AnnotationInspector();

    @Test
    public void testInspect() throws NoSuchMethodException, SecurityException
    {
        BuilderModel model = inspector.inspectBuilder(Builder.class);

        assertThat(model.getBuilderType(), is(equalTo((Class) Builder.class)));
        assertThat(model.getResultType(), is(equalTo((Class) Built.class)));
        assertThat(model.getProperties().size(), is(1));

        BuilderProperty property = model.getProperties().get(0);
        assertThat(property.getName(), is("description"));
        assertThat(property.getType(), is(equalTo((Type) String.class)));
        assertThat(property.getValidators().length, is(0));
    }

    private interface Builder
    {
        Builder description(String description);

        Built build();
    }

    private interface Built
    {

        String getDescription();
    }
}
