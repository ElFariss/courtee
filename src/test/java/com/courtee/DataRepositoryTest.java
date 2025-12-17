package com.courtee;

import com.courtee.model.Court;
import com.courtee.model.Venue;
import com.courtee.utils.DataRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DataRepositoryTest {
    
    @Test
    public void testGetVenues() {
        List<Venue> venues = DataRepository.getVenues();
        
        assertNotNull(venues);
        assertFalse(venues.isEmpty());
        assertEquals(6, venues.size());
    }
    
    @Test
    public void testVenueData() {
        List<Venue> venues = DataRepository.getVenues();
        
        Venue firstVenue = venues.get(0);
        assertEquals("longfield-sport-center", firstVenue.getId());
        assertEquals("Longfield Sport Center", firstVenue.getName());
        assertEquals("Lapangan Sepak Bola", firstVenue.getType());
        assertEquals("Jakarta Barat", firstVenue.getLocation());
        assertEquals(25000, firstVenue.getPricePerHour());
    }
    
    @Test
    public void testVenueTimeSlots() {
        List<Venue> venues = DataRepository.getVenues();
        
        for (Venue venue : venues) {
            assertNotNull(venue.getTimeSlots());
            assertFalse(venue.getTimeSlots().isEmpty());
        }
    }
    
    @Test
    public void testGetCourtsForVenue() {
        List<Court> courts = DataRepository.getCourtsForVenue("longfield-sport-center");
        
        assertNotNull(courts);
        assertEquals(2, courts.size());
    }
    
    @Test
    public void testCourtDetails() {
        List<Court> courts = DataRepository.getCourtsForVenue("longfield-sport-center");
        
        Court firstCourt = courts.get(0);
        assertEquals("lapangan-sejahtera", firstCourt.getId());
        assertEquals("Lapangan Sejahtera", firstCourt.getName());
        assertNotNull(firstCourt.getDescription());
        assertFalse(firstCourt.getTimeSlots().isEmpty());
    }
    
    @Test
    public void testCourtTimeSlots() {
        List<Court> courts = DataRepository.getCourtsForVenue("longfield-sport-center");
        
        for (Court court : courts) {
            assertNotNull(court.getTimeSlots());
            assertTrue(court.getTimeSlots().size() >= 8);
        }
    }
    
    @Test
    public void testNonExistentVenue() {
        List<Court> courts = DataRepository.getCourtsForVenue("non-existent-venue");
        
        assertNotNull(courts);
        assertTrue(courts.isEmpty());
    }
    
    @Test
    public void testVenueUniqueness() {
        List<Venue> venues = DataRepository.getVenues();
        
        Venue venue1 = venues.get(0);
        Venue venue2 = venues.get(1);
        
        assertNotEquals(venue1.getId(), venue2.getId());
        assertNotEquals(venue1.getName(), venue2.getName());
    }
}
