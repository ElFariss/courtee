package com.courtee;

import com.courtee.model.Booking;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class BookingTest {
    
    private Booking booking;
    
    @BeforeEach
    public void setUp() {
        booking = new Booking("Longfield Sport Center", "Lapangan Makmur", 
                             "20 Desember 2025", "09.00-10.00", 25000);
    }
    
    @Test
    public void testBookingCreation() {
        assertNotNull(booking);
        assertEquals("Longfield Sport Center", booking.getVenueName());
        assertEquals("Lapangan Makmur", booking.getCourtName());
        assertEquals("20 Desember 2025", booking.getDate());
        assertEquals("09.00-10.00", booking.getTime());
        assertEquals(25000, booking.getPrice());
    }
    
    @Test
    public void testFormattedPrice() {
        String formattedPrice = booking.getFormattedPrice();
        assertNotNull(formattedPrice);
        assertTrue(formattedPrice.contains("Rp"));
        assertTrue(formattedPrice.contains("25000"));
    }
    
    @Test
    public void testBookingSetters() {
        booking.setVenueName("New Venue");
        booking.setCourtName("New Court");
        booking.setDate("21 Desember 2025");
        booking.setTime("10.00-11.00");
        booking.setPrice(30000);
        
        assertEquals("New Venue", booking.getVenueName());
        assertEquals("New Court", booking.getCourtName());
        assertEquals("21 Desember 2025", booking.getDate());
        assertEquals("10.00-11.00", booking.getTime());
        assertEquals(30000, booking.getPrice());
    }
    
    @Test
    public void testMultipleBookings() {
        Booking booking1 = new Booking("Venue A", "Court 1", "20 Dec", "08.00-09.00", 20000);
        Booking booking2 = new Booking("Venue B", "Court 2", "21 Dec", "09.00-10.00", 25000);
        
        assertNotEquals(booking1.getVenueName(), booking2.getVenueName());
        assertNotEquals(booking1.getPrice(), booking2.getPrice());
    }
}
