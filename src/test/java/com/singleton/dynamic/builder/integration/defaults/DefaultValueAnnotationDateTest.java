package com.singleton.dynamic.builder.integration.defaults;

import static com.singleton.dynamic.builder.defaults.DefaultValueType.CURRENT_DATE_TIME;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

import com.singleton.dynamic.builder.DynamicBuilderFactory;
import com.singleton.dynamic.builder.annotation.DefaultValue;

/**
 * Test to validate that if builder methods of parameter type {@link Date} is not called then specified default value is
 * used.
 *
 * @author Prateek Kansal
 */
public class DefaultValueAnnotationDateTest
{
    @Test
    public void testDefaultValue_NullDateValue() throws InterruptedException
    {
        Date beginDate = new Date();
        Thread.sleep(1);

        Date actualDate = new DynamicBuilderFactory().createBuilderForClass(DefaultValueDateObjectBuilder.class)
                .intValue(2).build().getDateValue();
        Thread.sleep(1);

        Date endDate = new Date();

        assertTrue(actualDate.after(beginDate));
        assertTrue(actualDate.before(endDate));
    }

    private interface DefaultValueDateObjectBuilder
    {
        DefaultValueDateObjectBuilder dateValue(@DefaultValue(CURRENT_DATE_TIME) Date value);

        DefaultValueDateObjectBuilder intValue(int value);

        DefaultValueDateObject build();
    }

    private interface DefaultValueDateObject
    {
        Date getDateValue();

        int getIntValue();
    }
}
