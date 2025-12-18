package com.courtee.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.courtee.database.DatabaseConnection;
import com.courtee.database.dao.BookingDAO;
import com.courtee.database.dao.CourtDAO;
import com.courtee.database.dao.TimeSlotDAO;
import com.courtee.database.dao.VenueDAO;
import com.courtee.model.Booking;
import com.courtee.model.TimeSlot;
import com.courtee.model.Venue;

/**
 * Test class for TimeSlotDAO
 * Tests time slot operations and date-specific availability logic
 */
public class TimeSlotDAOTest {

   private TimeSlotDAO timeSlotDAO;
   private BookingDAO bookingDAO;
   private VenueDAO venueDAO;
   private CourtDAO courtDAO;
   private Connection testConnection;

   @BeforeEach
   public void setUp() throws SQLException {
      // Initialize test database
      DatabaseConnection.initialize("test_timeslot_dao.db");
      testConnection = DatabaseConnection.getConnection();
      DatabaseConnection.createTables(testConnection);

      timeSlotDAO = new TimeSlotDAO();
      bookingDAO = new BookingDAO();
      venueDAO = new VenueDAO();
      courtDAO = new CourtDAO();
   }

   @AfterEach
   public void tearDown() throws SQLException {
      if (testConnection != null && !testConnection.isClosed()) {
         testConnection.close();
      }
      // Clean up test database file
      java.io.File dbFile = new java.io.File("test_timeslot_dao.db");
      if (dbFile.exists()) {
         dbFile.delete();
      }
   }

   @Test
   public void testInsertTimeSlot() throws SQLException {
      TimeSlot timeSlot = new TimeSlot("08:00 - 09:00", 50000, true);

      assertDoesNotThrow(() -> {
         timeSlotDAO.insertTimeSlot("court-1", timeSlot);
      });
   }

   @Test
   public void testUpdateAvailability() throws SQLException {
      TimeSlot timeSlot = new TimeSlot("08:00 - 09:00", 50000, true);
      timeSlotDAO.insertTimeSlot("court-1", timeSlot);

      // Update to unavailable
      timeSlotDAO.updateAvailability("court-1", "08:00 - 09:00", false);

      // Verify update
      boolean available = timeSlotDAO.isAvailable("court-1", "08:00 - 09:00");
      assertFalse(available, "Time slot should be unavailable after update");
   }

   @Test
   public void testIsAvailable() throws SQLException {
      TimeSlot timeSlot = new TimeSlot("09:00 - 10:00", 60000, true);
      timeSlotDAO.insertTimeSlot("court-2", timeSlot);

      boolean available = timeSlotDAO.isAvailable("court-2", "09:00 - 10:00");

      assertTrue(available, "Newly inserted time slot should be available");
   }

   @Test
   public void testIsAvailableForNonExistentSlot() throws SQLException {
      boolean available = timeSlotDAO.isAvailable("non-existent-court", "10:00 - 11:00");

      assertFalse(available, "Non-existent time slot should return false");
   }

   @Test
   public void testIsAvailableForDate_NoBookings() throws SQLException {
      // Setup: Create court in database first
      setupTestCourt("court-1", "Test Court");

      TimeSlot timeSlot = new TimeSlot("08:00 - 09:00", 50000, true);
      timeSlotDAO.insertTimeSlot("court-1", timeSlot);

      // Check availability for specific date (no bookings exist)
      boolean available = timeSlotDAO.isAvailableForDate("court-1", "08:00 - 09:00", "20 Desember 2025");

      assertTrue(available, "Time slot should be available when no bookings exist");
   }

   @Test
   public void testIsAvailableForDate_WithBooking() throws SQLException {
      // Setup: Create court
      setupTestCourt("court-2", "Court Two");

      TimeSlot timeSlot = new TimeSlot("09:00 - 10:00", 60000, true);
      timeSlotDAO.insertTimeSlot("court-2", timeSlot);

      // Create a booking for specific date
      Booking booking = new Booking("Test Venue", "Court Two",
            "20 Desember 2025", "09:00 - 10:00", 60000);
      bookingDAO.insertBooking(booking);

      // Check availability for the same date
      boolean available = timeSlotDAO.isAvailableForDate("court-2", "09:00 - 10:00", "20 Desember 2025");

      assertFalse(available, "Time slot should be unavailable when booking exists for that date");
   }

   @Test
   public void testIsAvailableForDate_DifferentDate() throws SQLException {
      // Setup: Create court
      setupTestCourt("court-3", "Court Three");

      TimeSlot timeSlot = new TimeSlot("10:00 - 11:00", 70000, true);
      timeSlotDAO.insertTimeSlot("court-3", timeSlot);

      // Create booking for Dec 20
      Booking booking = new Booking("Test Venue", "Court Three",
            "20 Desember 2025", "10:00 - 11:00", 70000);
      bookingDAO.insertBooking(booking);

      // Check availability for Dec 21 (different date)
      boolean available = timeSlotDAO.isAvailableForDate("court-3", "10:00 - 11:00", "21 Desember 2025");

      assertTrue(available, "Time slot should be available for different date even if booked on another date");
   }

   @Test
   public void testIsAvailableForDate_DifferentTime() throws SQLException {
      // Setup: Create court
      setupTestCourt("court-4", "Court Four");

      TimeSlot timeSlot1 = new TimeSlot("08:00 - 09:00", 50000, true);
      TimeSlot timeSlot2 = new TimeSlot("09:00 - 10:00", 50000, true);
      timeSlotDAO.insertTimeSlot("court-4", timeSlot1);
      timeSlotDAO.insertTimeSlot("court-4", timeSlot2);

      // Book 08:00 - 09:00 on Dec 20
      Booking booking = new Booking("Test Venue", "Court Four",
            "20 Desember 2025", "08:00 - 09:00", 50000);
      bookingDAO.insertBooking(booking);

      // Check availability for 09:00 - 10:00 (different time, same date)
      boolean available = timeSlotDAO.isAvailableForDate("court-4", "09:00 - 10:00", "20 Desember 2025");

      assertTrue(available, "Different time slot should be available even if another time is booked");
   }

   @Test
   public void testIsAvailableForDate_MultipleBookingsSameSlot() throws SQLException {
      // Setup: Create court
      setupTestCourt("court-5", "Court Five");

      TimeSlot timeSlot = new TimeSlot("14:00 - 15:00", 80000, true);
      timeSlotDAO.insertTimeSlot("court-5", timeSlot);

      // Book the same slot on different dates
      bookingDAO.insertBooking(new Booking("Test Venue", "Court Five",
            "20 Desember 2025", "14:00 - 15:00", 80000));
      bookingDAO.insertBooking(new Booking("Test Venue", "Court Five",
            "21 Desember 2025", "14:00 - 15:00", 80000));

      // Check Dec 20 - should be unavailable
      boolean availableDec20 = timeSlotDAO.isAvailableForDate("court-5", "14:00 - 15:00", "20 Desember 2025");
      assertFalse(availableDec20, "Dec 20 should be unavailable");

      // Check Dec 21 - should be unavailable
      boolean availableDec21 = timeSlotDAO.isAvailableForDate("court-5", "14:00 - 15:00", "21 Desember 2025");
      assertFalse(availableDec21, "Dec 21 should be unavailable");

      // Check Dec 22 - should be available
      boolean availableDec22 = timeSlotDAO.isAvailableForDate("court-5", "14:00 - 15:00", "22 Desember 2025");
      assertTrue(availableDec22, "Dec 22 should be available");
   }

   @Test
   public void testToggleAvailability() throws SQLException {
      TimeSlot timeSlot = new TimeSlot("15:00 - 16:00", 90000, true);
      timeSlotDAO.insertTimeSlot("court-6", timeSlot);

      // Initially available
      assertTrue(timeSlotDAO.isAvailable("court-6", "15:00 - 16:00"));

      // Toggle to unavailable
      timeSlotDAO.updateAvailability("court-6", "15:00 - 16:00", false);
      assertFalse(timeSlotDAO.isAvailable("court-6", "15:00 - 16:00"));

      // Toggle back to available
      timeSlotDAO.updateAvailability("court-6", "15:00 - 16:00", true);
      assertTrue(timeSlotDAO.isAvailable("court-6", "15:00 - 16:00"));
   }

   /**
    * Helper method to set up a test court in the database
    */
   private void setupTestCourt(String courtId, String courtName) throws SQLException {
      // Create a venue first
      Venue venue = new Venue("test-venue", "Test Venue", "Football",
            "Jakarta", 50000, "test.jpg");
      venueDAO.insertVenue(venue);

      // Create court with SQL directly to avoid image loading issues
      String sql = "INSERT INTO courts (court_id, venue_id, name, description) VALUES (?, ?, ?, ?)";
      try (java.sql.PreparedStatement pstmt = testConnection.prepareStatement(sql)) {
         pstmt.setString(1, courtId);
         pstmt.setString(2, "test-venue");
         pstmt.setString(3, courtName);
         pstmt.setString(4, "Test court description");
         pstmt.executeUpdate();
      }
   }
}
