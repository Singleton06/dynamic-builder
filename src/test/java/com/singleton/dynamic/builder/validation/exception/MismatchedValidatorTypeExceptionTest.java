package com.singleton.dynamic.builder.validation.exception;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * Test class for {@link MismatchedValidatorTypeException}.
 * 
 * @author Dustin Singleton
 */
public class MismatchedValidatorTypeExceptionTest
{
    @Test
    public void testConstructor()
    {
        MismatchedValidatorTypeException exception = new MismatchedValidatorTypeException("message");
        
        assertThat(exception.getMessage(), is("message"));
    }
}
