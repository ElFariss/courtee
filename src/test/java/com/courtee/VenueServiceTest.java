package com.courtee;

import com.courtee.model.Court;
import com.courtee.model.Venue;
import com.courtee.service.VenueService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class VenueServiceTest {
    
    private VenueService venueService;
    
    @BeforeEach
    public void setUp() {
        venueService = new VenueService();
    }
    
    @Test
    public void testGetAllVenues() {
        List<Venue> venues = venueService.getAllVenues();
        
        assertNotNull(venues);
        assertEquals(6, venues.size());
    }
    
    @Test
    public void testGetVenueById() {
        Venue venue = venueService.getVenueById("longfield-sport-center");
        
        assertNotNull(venue);
        assertEquals("Longfield Sport Center", venue.getName());
    }
    
    @Test
    public void testGetVenueByIdNotFound() {
        Venue venue = venueService.getVenueById("non-existent");
        
        assertNull(venue);
    }
    
    @Test
    public void testGetCourtsForVenue() {
        List<Court> courts = venueService.getCourtsForVenue("longfield-sport-center");
        
        assertNotNull(courts);
        assertEquals(2, courts.size());
    }
    
    @Test
    public void testGetCourtsForAllVenues() {
        // Test all venues have courts
        String[] venueIds = {"longfield-sport-center", "culture-padel", "balistic-badminton", 
                            "sumber-sari-jaya", "singhasari-tennis-club", "balistic-badminton-2"};
        
        for (String venueId : venueIds) {
            List<Court> courts = venueService.getCourtsForVenue(venueId);
            assertNotNull(courts);
            assertEquals(2, courts.size(), "Venue " + venueId + " should have 2 courts");
            
            // Verify each court has time slots
            for (Court court : courts) {
                assertFalse(court.getTimeSlots().isEmpty());
            }
        }
    }
}
