package com.singleton.dynamic.builder.internal.representation;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.singleton.dynamic.builder.representation.BuilderProperty;

/**
 * <p>
 * Tests for {@link BuilderModelImpl}.
 * </p>
 *
 * @author Brandon Callison
 */
@SuppressWarnings({ "javadoc" })
public class BuilderModelImplTest
{
    private static final Class<?> BUILDER_TYPE = Integer.class;
    private static final Class<?> RESULT_TYPE = String.class;
    private static final BuilderProperty BUILDER_PROPERTY = mock(BuilderProperty.class);
    private final BuilderModelImpl.Builder builder = new BuilderModelImpl.Builder();

    @Before
    public void setUp()
    {
        builder.builderType(BUILDER_TYPE).resultType(RESULT_TYPE).properties(Arrays.asList(BUILDER_PROPERTY));
    }

    @SuppressWarnings("rawtypes")
    @Test
    public void testBuild()
    {
        BuilderModelImpl model = builder.build();

        assertThat((Class) model.getBuilderType(), is(equalTo((Class) BUILDER_TYPE)));
        assertThat((Class) model.getResultType(), is(equalTo((Class) RESULT_TYPE)));
        assertThat(model.getProperties(), is(Arrays.asList(BUILDER_PROPERTY)));
    }
}
