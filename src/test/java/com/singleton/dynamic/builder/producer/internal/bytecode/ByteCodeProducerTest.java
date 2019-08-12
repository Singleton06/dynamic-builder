package com.singleton.dynamic.builder.producer.internal.bytecode;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.singleton.dynamic.builder.internal.representation.BuilderModel;
import com.singleton.dynamic.builder.internal.representation.BuilderProperty;
import com.singleton.dynamic.builder.validation.NotParameterValidator;

/**
 * <p>
 * Tests for {@link ByteCodeProducer}.
 * </p>
 *
 * @author Brandon Callison
 */
@SuppressWarnings({ "javadoc", "nls", "unchecked", "rawtypes" })
public class ByteCodeProducerTest {
    private BuilderModel model;
    private ByteCodeProducer producer;

    @Before
    public void setUp() {
        BuilderProperty branchesProperty = new BuilderProperty.Builder().name("branches").type(int.class)
                .validators(new NotParameterValidator[0]).build();

        BuilderProperty typeProperty = new BuilderProperty.Builder().name("type").type(String.class)
                .validators(new NotParameterValidator[0]).build();

        model = mock(BuilderModel.class);
        
        when(model.getBuilderType()).thenReturn((Class) TreeBuilder.class);
        when(model.getResultType()).thenReturn((Class) Tree.class);
        when(model.getProperties()).thenReturn(Arrays.asList(branchesProperty, typeProperty));

        producer = new ByteCodeProducer();
    }

    @Test
    public void test() throws NoSuchFieldException, SecurityException, InstantiationException, IllegalAccessException {
        Class<TreeBuilder> builderClass = (Class<TreeBuilder>) producer.produceBuilder(model);
        TreeBuilder treeBuilder = builderClass.newInstance();

        treeBuilder.branches(5);
        treeBuilder.type("Birch");

        Tree tree = treeBuilder.build();

        assertThat(tree.getBranches(), is(5));
        assertThat(tree.getType(), is("Birch"));
    }

    public static interface TreeBuilder {
        TreeBuilder branches(int branches);

        TreeBuilder type(String type);

        Tree build();
    }

    public interface Tree {
        String getType();

        int getBranches();
    }
}
