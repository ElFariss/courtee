package com.courtee.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.courtee.database.DatabaseConnection;
import com.courtee.database.dao.BookingDAO;
import com.courtee.model.Booking;

/**
 * Test class for BookingDAO
 * Tests CRUD operations for bookings including date-specific functionality
 */
public class BookingDAOTest {

   private BookingDAO bookingDAO;
   private Connection testConnection;

   @BeforeEach
   public void setUp() throws SQLException {
      // Initialize test database
      DatabaseConnection.initialize("test_booking_dao.db");
      testConnection = DatabaseConnection.getConnection();
      DatabaseConnection.createTables(testConnection);

      bookingDAO = new BookingDAO();
   }

   @AfterEach
   public void tearDown() throws SQLException {
      if (testConnection != null && !testConnection.isClosed()) {
         testConnection.close();
      }
      // Clean up test database file
      java.io.File dbFile = new java.io.File("test_booking_dao.db");
      if (dbFile.exists()) {
         dbFile.delete();
      }
   }

   @Test
   public void testInsertBooking() throws SQLException {
      Booking booking = new Booking("Test Venue", "Test Court",
            "20 Desember 2025", "08:00 - 09:00", 50000);

      int bookingId = bookingDAO.insertBooking(booking);

      assertTrue(bookingId > 0, "Booking ID should be positive");
   }

   @Test
   public void testInsertBookingReturnsValidId() throws SQLException {
      Booking booking1 = new Booking("Venue A", "Court A",
            "20 Desember 2025", "08:00 - 09:00", 50000);
      Booking booking2 = new Booking("Venue B", "Court B",
            "21 Desember 2025", "09:00 - 10:00", 60000);

      int id1 = bookingDAO.insertBooking(booking1);
      int id2 = bookingDAO.insertBooking(booking2);

      assertTrue(id1 > 0);
      assertTrue(id2 > 0);
      assertNotEquals(id1, id2, "Each booking should have unique ID");
   }

   @Test
   public void testGetAllBookings() throws SQLException {
      // Insert multiple bookings
      bookingDAO.insertBooking(new Booking("Venue 1", "Court 1",
            "20 Desember 2025", "08:00 - 09:00", 50000));
      bookingDAO.insertBooking(new Booking("Venue 2", "Court 2",
            "21 Desember 2025", "09:00 - 10:00", 60000));
      bookingDAO.insertBooking(new Booking("Venue 3", "Court 3",
            "22 Desember 2025", "10:00 - 11:00", 70000));

      List<Booking> bookings = bookingDAO.getAllBookings();

      assertNotNull(bookings);
      assertEquals(3, bookings.size());
   }

   @Test
   public void testGetAllBookingsEmpty() throws SQLException {
      List<Booking> bookings = bookingDAO.getAllBookings();

      assertNotNull(bookings);
      assertTrue(bookings.isEmpty());
   }

   @Test
   public void testUpdatePaymentStatus() throws SQLException {
      Booking booking = new Booking("Test Venue", "Test Court",
            "20 Desember 2025", "08:00 - 09:00", 50000);
      int bookingId = bookingDAO.insertBooking(booking);

      assertDoesNotThrow(() -> {
         bookingDAO.updatePaymentStatus(bookingId, "QRIS", "SUCCESS");
      });

      // Verify update (would need a getBookingById method in DAO to fully test)
      // For now, just ensure no exception is thrown
   }

   @Test
   public void testDeleteBooking() throws SQLException {
      Booking booking = new Booking("Test Venue", "Test Court",
            "20 Desember 2025", "08:00 - 09:00", 50000);
      int bookingId = bookingDAO.insertBooking(booking);

      boolean deleted = bookingDAO.deleteBooking(bookingId);

      assertTrue(deleted, "Booking should be deleted successfully");

      // Verify deletion
      List<Booking> bookings = bookingDAO.getAllBookings();
      assertTrue(bookings.isEmpty(), "No bookings should remain after deletion");
   }

   @Test
   public void testDeleteNonExistentBooking() throws SQLException {
      boolean deleted = bookingDAO.deleteBooking(999);

      assertFalse(deleted, "Deleting non-existent booking should return false");
   }

   @Test
   public void testDateSpecificBookings() throws SQLException {
      // Test that bookings for different dates are independent
      Booking booking1 = new Booking("Venue A", "Court A",
            "20 Desember 2025", "08:00 - 09:00", 50000);
      Booking booking2 = new Booking("Venue A", "Court A",
            "21 Desember 2025", "08:00 - 09:00", 50000);

      int id1 = bookingDAO.insertBooking(booking1);
      int id2 = bookingDAO.insertBooking(booking2);

      assertTrue(id1 > 0 && id2 > 0);

      List<Booking> allBookings = bookingDAO.getAllBookings();
      assertEquals(2, allBookings.size(), "Should have 2 bookings for same court/time but different dates");
   }

   @Test
   public void testBookingWithCourtId() throws SQLException {
      Booking booking = new Booking("Venue A", "Court A", "court-id-123",
            "20 Desember 2025", "08:00 - 09:00", 50000);

      int bookingId = bookingDAO.insertBooking(booking);

      assertTrue(bookingId > 0);

      // Verify courtId is stored (would need additional DAO method to fully verify)
      List<Booking> bookings = bookingDAO.getAllBookings();
      assertEquals(1, bookings.size());
   }

   @Test
   public void testMultipleBookingsSameVenueDifferentCourts() throws SQLException {
      bookingDAO.insertBooking(new Booking("Same Venue", "Court 1",
            "20 Desember 2025", "08:00 - 09:00", 50000));
      bookingDAO.insertBooking(new Booking("Same Venue", "Court 2",
            "20 Desember 2025", "08:00 - 09:00", 50000));
      bookingDAO.insertBooking(new Booking("Same Venue", "Court 3",
            "20 Desember 2025", "08:00 - 09:00", 50000));

      List<Booking> bookings = bookingDAO.getAllBookings();

      assertEquals(3, bookings.size());
      assertTrue(bookings.stream().allMatch(b -> b.getVenueName().equals("Same Venue")));
   }

   @Test
   public void testBookingDataIntegrity() throws SQLException {
      String venueName = "Longfield Sport Center";
      String courtName = "Lapangan Makmur";
      String date = "20 Desember 2025";
      String time = "14:00 - 15:00";
      double price = 75000;

      Booking booking = new Booking(venueName, courtName, date, time, price);
      int bookingId = bookingDAO.insertBooking(booking);

      List<Booking> bookings = bookingDAO.getAllBookings();
      Booking retrieved = bookings.get(0);

      assertEquals(venueName, retrieved.getVenueName());
      assertEquals(courtName, retrieved.getCourtName());
      assertEquals(date, retrieved.getDate());
      assertEquals(time, retrieved.getTime());
      assertEquals(price, retrieved.getPrice());
   }
}
