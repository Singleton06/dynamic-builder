package com.singleton.dynamic.builder.producer.internal.bytecode;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.singleton.dynamic.builder.representation.BuilderModel;
import com.singleton.dynamic.builder.representation.BuilderProperty;
import com.singleton.dynamic.builder.validation.NotParameterValidator;

/**
 * <p>
 * Tests for {@link ByteCodeProducer}.
 * </p>
 *
 * @author Brandon Callison
 */
@SuppressWarnings({ "javadoc", "nls", "unchecked", "rawtypes" })
public class ByteCodeProducerTest
{
    private BuilderModel model;
    private ByteCodeProducer producer;

    @Before
    public void setUp()
    {
        BuilderProperty branchesProperty = mock(BuilderProperty.class);
        when(branchesProperty.getName()).thenReturn("branches");
        when(branchesProperty.getType()).thenReturn(int.class);
        when(branchesProperty.getValidators()).thenReturn(new NotParameterValidator[0]);

        BuilderProperty typeProperty = mock(BuilderProperty.class);
        when(typeProperty.getName()).thenReturn("type");
        when(typeProperty.getType()).thenReturn(String.class);
        when(typeProperty.getValidators()).thenReturn(new NotParameterValidator[0]);

        model = mock(BuilderModel.class);
        when(model.getBuilderType()).thenReturn((Class) TreeBuilder.class);
        when(model.getResultType()).thenReturn((Class) Tree.class);
        when(model.getProperties()).thenReturn(Arrays.asList(branchesProperty, typeProperty));

        producer = new ByteCodeProducer();
    }

    @Test
    public void test() throws NoSuchFieldException, SecurityException, InstantiationException, IllegalAccessException
    {
        Class<TreeBuilder> builderClass = (Class<TreeBuilder>) producer.produceBuilder(model);
        TreeBuilder treeBuilder = builderClass.newInstance();

        treeBuilder.branches(5);
        treeBuilder.type("Birch");

        Tree tree = treeBuilder.build();

        assertThat(tree.getBranches(), is(5));
        assertThat(tree.getType(), is("Birch"));
    }

    private static interface TreeBuilder
    {
        TreeBuilder branches(int branches);

        TreeBuilder type(String type);

        Tree build();
    }

    private interface Tree
    {
        String getType();

        int getBranches();
    }
}
