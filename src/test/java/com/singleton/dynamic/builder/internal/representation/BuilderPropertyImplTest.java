package com.singleton.dynamic.builder.internal.representation;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Type;

import org.junit.Before;
import org.junit.Test;

import com.singleton.dynamic.builder.validation.NotParameterValidator;

/**
 * <p>
 * Tests for {@link BuilderPropertyImpl}.
 * </p>
 *
 * @author Brandon Callison
 */
@SuppressWarnings({ "javadoc", "nls" })
public class BuilderPropertyImplTest
{
    private static final String PROPERTY_NAME = "prop";
    private static final Type PROPERTY_TYPE = Integer.class;
    private static final NotParameterValidator[] validators = new NotParameterValidator[] {
            NotParameterValidator.EMPTY };
    private final BuilderPropertyImpl.Builder builder = new BuilderPropertyImpl.Builder();

    @Before
    public void setUp()
    {
        builder.name(PROPERTY_NAME).type(PROPERTY_TYPE).validators(validators);
    }

    @Test
    public void testBuild()
    {
        BuilderPropertyImpl property = builder.build();

        assertThat(property.getName(), is(PROPERTY_NAME));
        assertThat(property.getType(), is(PROPERTY_TYPE));
        assertThat(property.getValidators(), is(validators));
    }
}
