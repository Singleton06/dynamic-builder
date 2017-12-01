package com.singleton.dynamic.builder.proxy.supportedtypes;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.singleton.dynamic.builder.proxy.ProxyBuilderFactory;

/**
 * Test to ensure that simple objects are supported.
 * 
 * @author Dustin Singleton
 */
@SuppressWarnings({ "javadoc", "nls" })
public class ObjectsTest
{
    private final ProxyBuilderFactory factory = new ProxyBuilderFactory();

    @Test
    public void testWithSimpleObject()
    {
        CustomObject simpleObject = new CustomObject(1, "Dustin");
        String stringValue = "stringValue";

        ObjectBuilder builder = factory.createBuilderForClass(ObjectBuilder.class);
        BuiltObject builtObject = builder.customObject(simpleObject).stringValue(stringValue).build();

        assertThat(builtObject.getCustomObject(), is(simpleObject));
        assertThat(builtObject.getStringValue(), is(stringValue));
    }

    private interface ObjectBuilder
    {
        ObjectBuilder customObject(CustomObject simpleObject);

        ObjectBuilder stringValue(String stringValue);

        BuiltObject build();
    }

    private interface BuiltObject
    {
        CustomObject getCustomObject();

        String getStringValue();
    }

    private class CustomObject
    {
        private final int id;
        private final String firstName;

        public CustomObject(int id, String firstName)
        {
            super();
            this.id = id;
            this.firstName = firstName;
        }

        @Override
        public int hashCode()
        {
            final int prime = 31;
            int result = 1;
            result = prime * result + getOuterType().hashCode();
            result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
            result = prime * result + id;
            return result;
        }

        @Override
        public boolean equals(Object obj)
        {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            CustomObject other = (CustomObject) obj;
            if (!getOuterType().equals(other.getOuterType()))
                return false;
            if (firstName == null)
            {
                if (other.firstName != null)
                    return false;
            }
            else if (!firstName.equals(other.firstName))
                return false;
            if (id != other.id)
                return false;
            return true;
        }

        private ObjectsTest getOuterType()
        {
            return ObjectsTest.this;
        }
    }
}