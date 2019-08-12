package com.singleton.dynamic.builder.sanitation.integration;

import static com.singleton.dynamic.builder.sanitation.SanitizeValueType.CURRENT_DATE_TIME;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

import com.singleton.dynamic.builder.DynamicBuilderFactory;
import com.singleton.dynamic.builder.annotation.SanitizeValue;

/**
 * Test to validate the sanitize annotation for the builder method parameter of
 * type {@link Date}.
 *
 * @author Prateek Kansal
 */
@SuppressWarnings("javadoc")
public class SanitizeValueAnnotationDateTest {
    @Test
    public void testSanitizeDate_NullValue() throws InterruptedException {
        Date beginDate = new Date();
        Thread.sleep(1);

        Date actualDate = DynamicBuilderFactory.getInstance()
                .createBuilderForClass(SanitizeDateValueObjectBuilder.class).dateValue(null).build().getDateValue();
        Thread.sleep(1);

        Date endDate = new Date();

        assertTrue(actualDate.after(beginDate));
        assertTrue(actualDate.before(endDate));
    }

    public interface SanitizeDateValueObjectBuilder {
        SanitizeDateValueObjectBuilder dateValue(@SanitizeValue(CURRENT_DATE_TIME) Date value);

        SanitizeDateValueObject build();
    }

    public interface SanitizeDateValueObject {
        Date getDateValue();
    }
}
