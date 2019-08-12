package com.singleton.dynamic.builder.producer;

import java.util.HashMap;
import java.util.Map;

import com.singleton.dynamic.builder.DynamicBuilderFactory;
import com.singleton.dynamic.builder.internal.inspection.AnnotationInspector;
import com.singleton.dynamic.builder.internal.representation.BuilderModel;
import com.singleton.dynamic.builder.producer.internal.bytecode.ByteCodeProducer;

/**
 * Creates builders using bytecode generation.
 *
 * @author Brandon Callison
 */
public class ByteCodeDynamicBuilderFactory implements DynamicBuilderFactory
{
    private final Map<Class<?>, Class<?>> builderImplementations = new HashMap<Class<?>, Class<?>>();

    @SuppressWarnings({ "unchecked" })
    @Override
    public <T> T createBuilderForClass(Class<T> clazz)
    {
        Class<?> builderImplementation = builderImplementations.get(clazz);

        if (builderImplementation == null)
        {
            builderImplementation = createNewBuilderForClass(clazz);
            builderImplementations.put(clazz, builderImplementation);
        }

        try
        {
            return (T) builderImplementation.newInstance();
        }
        catch (Exception e)
        {
            throw new RuntimeException("Bad Problem", e); //$NON-NLS-1$
        }
    }

    private static Class<?> createNewBuilderForClass(Class<?> clazz)
    {
        try
        {
            BuilderModel model = new AnnotationInspector().inspectBuilder(clazz);
            return new ByteCodeProducer().produceBuilder(model);
        }
        catch (Exception e)
        {
            throw new RuntimeException("Bad Problem", e); //$NON-NLS-1$

        }
    }
}
