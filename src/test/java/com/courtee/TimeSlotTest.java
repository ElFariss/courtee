package com.courtee;

import com.courtee.model.TimeSlot;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class TimeSlotTest {
    
    private TimeSlot timeSlot;
    
    @BeforeEach
    public void setUp() {
        timeSlot = new TimeSlot("08.00-09.00", 25000, true);
    }
    
    @Test
    public void testTimeSlotCreation() {
        assertNotNull(timeSlot);
        assertEquals("08.00-09.00", timeSlot.getTime());
        assertEquals(25000, timeSlot.getPrice());
        assertTrue(timeSlot.isAvailable());
    }
    
    @Test
    public void testTimeSlotAvailability() {
        timeSlot.setAvailable(false);
        assertFalse(timeSlot.isAvailable());
        
        timeSlot.setAvailable(true);
        assertTrue(timeSlot.isAvailable());
    }
    
    @Test
    public void testFormattedPrice() {
        String formattedPrice = timeSlot.getFormattedPrice();
        assertNotNull(formattedPrice);
        assertTrue(formattedPrice.contains("Rp"));
        assertTrue(formattedPrice.contains("25000"));
    }
    
    @Test
    public void testTimeSlotSetters() {
        timeSlot.setTime("10.00-11.00");
        timeSlot.setPrice(30000);
        
        assertEquals("10.00-11.00", timeSlot.getTime());
        assertEquals(30000, timeSlot.getPrice());
    }
    
    @Test
    public void testUnavailableTimeSlot() {
        TimeSlot unavailableSlot = new TimeSlot("14.00-15.00", 20000, false);
        
        assertFalse(unavailableSlot.isAvailable());
        assertEquals("14.00-15.00", unavailableSlot.getTime());
    }
}
