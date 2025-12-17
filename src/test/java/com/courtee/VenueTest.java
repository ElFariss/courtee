package com.courtee;

import com.courtee.model.Venue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class VenueTest {
    
    private Venue venue;
    
    @BeforeEach
    public void setUp() {
        venue = new Venue("test-venue", "Test Venue", "Football Field", 
                         "Jakarta", 25000, "test-image.jpg");
    }
    
    @Test
    public void testVenueCreation() {
        assertNotNull(venue);
        assertEquals("test-venue", venue.getId());
        assertEquals("Test Venue", venue.getName());
        assertEquals("Football Field", venue.getType());
        assertEquals("Jakarta", venue.getLocation());
        assertEquals(25000, venue.getPricePerHour());
        assertEquals("test-image.jpg", venue.getImage());
    }
    
    @Test
    public void testAddTimeSlot() {
        venue.addTimeSlot("08.00-09.00");
        venue.addTimeSlot("09.00-10.00");
        
        assertEquals(2, venue.getTimeSlots().size());
        assertTrue(venue.getTimeSlots().contains("08.00-09.00"));
        assertTrue(venue.getTimeSlots().contains("09.00-10.00"));
    }
    
    @Test
    public void testSetTimeSlots() {
        venue.setTimeSlots(Arrays.asList("10.00-11.00", "11.00-12.00", "12.00-13.00"));
        
        assertEquals(3, venue.getTimeSlots().size());
        assertEquals("10.00-11.00", venue.getTimeSlots().get(0));
    }
    
    @Test
    public void testFormattedPrice() {
        String formattedPrice = venue.getFormattedPrice();
        assertNotNull(formattedPrice);
        assertTrue(formattedPrice.contains("Rp"));
        assertTrue(formattedPrice.contains("25"));
    }
    
    @Test
    public void testVenueSetters() {
        venue.setName("Updated Venue");
        venue.setLocation("Bandung");
        venue.setPricePerHour(30000);
        
        assertEquals("Updated Venue", venue.getName());
        assertEquals("Bandung", venue.getLocation());
        assertEquals(30000, venue.getPricePerHour());
    }
}
