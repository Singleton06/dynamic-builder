package com.singleton.dynamic.builder;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.singleton.dynamic.builder.proxy.ProxyBuilderFactory;

/**
 * Test class for {@link ProxyBuilderFactory}.
 * 
 * @author Dustin Singleton
 */
@SuppressWarnings({ "javadoc", "nls" })
public class ProxyBuilderFactoryTest
{
    @Test
    public void testBuilderConstruction() throws Exception
    {
        ProxyBuilderFactory factory = new ProxyBuilderFactory();

        System.out.println("Performing 500,000 cycles.");
        for (int i = 0; i < 500 * 1000; ++i)
        {
            AppointmentBuilder builder = factory.createBuilderForClass(AppointmentBuilder.class);

            builder.appointmentId(2).display("Appt A");
            Appointment appointment = builder.build();

            assertEquals(2L, appointment.getAppointmentId());
            assertEquals("Appt A", appointment.getDisplay());
        }
    }

    public interface AppointmentBuilder
    {
        AppointmentBuilder appointmentTypeId(long id);

        AppointmentBuilder appointmentId(long id);

        AppointmentBuilder display(String display);

        Appointment build();
    }

    public interface Appointment
    {
        long getAppointmentId();

        String getDisplay();

        long getAppointmentTypeId();
    }
}
