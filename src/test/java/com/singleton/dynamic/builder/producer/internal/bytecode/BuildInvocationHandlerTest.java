package com.singleton.dynamic.builder.producer.internal.bytecode;

import static org.hamcrest.CoreMatchers.instanceOf;
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
 * Tests for {@link BuildInvocationHandler}.
 * </p>
 *
 * @author Brandon Callison
 */
@SuppressWarnings({ "javadoc", "unchecked", "rawtypes", "nls" })
public class BuildInvocationHandlerTest
{
    private BuildInvocationHandler invocationHandler;

    @Before
    public void setUp()
    {
        BuilderProperty branchesProperty = mock(BuilderProperty.class);
        when(branchesProperty.getName()).thenReturn("branches");
        when(branchesProperty.getType()).thenReturn(int.class);
        when(branchesProperty.getValidators()).thenReturn(new NotParameterValidator[0]);

        BuilderProperty valueProperty = mock(BuilderProperty.class);
        when(valueProperty.getName()).thenReturn("type");
        when(valueProperty.getType()).thenReturn(String.class);
        when(valueProperty.getValidators()).thenReturn(new NotParameterValidator[0]);

        BuilderModel model = mock(BuilderModel.class);

        when(model.getBuilderType()).thenReturn((Class) TreeBuilder.class);
        when(model.getResultType()).thenReturn((Class) Tree.class);
        when(model.getProperties()).thenReturn(Arrays.asList(branchesProperty, valueProperty));

        invocationHandler = new BuildInvocationHandler(model);
    }

    @Test
    public void testInvoke() throws Throwable
    {
        TreeBuilder builder = new TreeBuilder();
        builder.branches = 9;
        builder.type = "Birch";

        Object result = invocationHandler.invoke(builder, null, null);
        assertThat(result, is(instanceOf(Tree.class)));

        Tree tree = (Tree) result;

        assertThat(tree.getBranches(), is(9));
        assertThat(tree.getType(), is("Birch"));
    }

    @SuppressWarnings("unused")
    private static class TreeBuilder
    {
        public String type;
        public int branches;

        public Tree build()
        {
            return null;
        }
    }

    private interface Tree
    {
        String getType();

        int getBranches();
    }
}
