package com.singleton.dynamic.builder.producer;

import static com.singleton.dynamic.builder.validation.NotParameterValidator.EMPTY;
import static com.singleton.dynamic.builder.validation.NotParameterValidator.NEGATIVE;
import static com.singleton.dynamic.builder.validation.NotParameterValidator.NULL;
import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import com.singleton.dynamic.builder.annotation.Not;
import com.singleton.dynamic.builder.producer.BuilderManufacturer;

/**
 * Test class for {@link BuilderManufacturer}.
 * 
 * @author Brandon Callison
 */
@SuppressWarnings({ "javadoc", "nls" })
public class BuilderManufacturerTest
{
    private static BuilderManufacturer factory = new BuilderManufacturer();

    @BeforeClass
    public static void setUpClass()
    {
        // Introduce the classes to the cache
        factory.createBuilderForClass(AppointmentBuilder.class).build();
    }

    @Test
    public void testBuilderCorrectness()
    {
        AppointmentBuilder builder = factory.createBuilderForClass(AppointmentBuilder.class);

        Appointment appointment = builder.appointmentId(2L).appointmentTypeId(-10L).display("Appt A").build();

        assertEquals(2L, (long) appointment.getAppointmentId());
        assertEquals("Appt A", appointment.getDisplay());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBuilderValidator_Negative()
    {
        AppointmentBuilder builder = factory.createBuilderForClass(AppointmentBuilder.class);

        builder.appointmentId(-2L).display("Appt A").build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBuilderValidator_Null()
    {
        AppointmentBuilder builder = factory.createBuilderForClass(AppointmentBuilder.class);

        builder.appointmentId(2L).display(null).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBuilderValidator_Empty()
    {
        AppointmentBuilder builder = factory.createBuilderForClass(AppointmentBuilder.class);

        builder.appointmentId(2L).display("").build();
    }

    @Test
    public void testBuilderConstruction() throws Exception
    {
        System.out.println("Performing 500,000 cycles.");

        for (int i = 0; i < 500 * 1000; ++i)
        {
            factory.createBuilderForClass(AppointmentBuilder.class).appointmentId(2L).display("Appt A").build();
        }
    }

    @Test
    public void testBuilderConstructionSameBuilder() throws Exception
    {
        System.out.println("Performing 500,000 cycles.");

        AppointmentBuilder builder = factory.createBuilderForClass(AppointmentBuilder.class);

        for (int i = 0; i < 500 * 1000; ++i)
        {
            builder.appointmentId(2L).display("Appt A").build();
        }
    }

    public interface AppointmentBuilder
    {
        AppointmentBuilder appointmentTypeId(Long id);

        AppointmentBuilder appointmentId(@Not(NEGATIVE) Long id);

        AppointmentBuilder display(@Not({ NULL, EMPTY }) String display);

        Appointment build();
    }

    public interface Appointment
    {
        Long getAppointmentId();

        String getDisplay();

        Long getAppointmentTypeId();
    }
}
