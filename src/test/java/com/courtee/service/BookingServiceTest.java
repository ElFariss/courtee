package com.courtee.service;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.courtee.database.DatabaseConnection;
import com.courtee.model.Booking;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookingServiceTest {

   private static BookingService bookingService;
   private static final String TEST_DB = "test_booking_service.db";

   @BeforeAll
   public static void setUpClass() throws SQLException {
      // Delete test database if exists
      File dbFile = new File(TEST_DB);
      if (dbFile.exists()) {
         dbFile.delete();
      }

      // Initialize test database
      DatabaseConnection.initialize(TEST_DB);
      bookingService = new BookingService();

      // Create tables
      try (Connection conn = DatabaseConnection.getConnection()) {
         DatabaseConnection.createTables(conn);
      }
   }

   @AfterAll
   public static void tearDownClass() {
      // Clean up test database
      File dbFile = new File(TEST_DB);
      if (dbFile.exists()) {
         dbFile.delete();
      }
   }

   @Test
   @Order(1)
   public void testAddBooking() {
      Booking booking = new Booking("Test Venue", "Court A",
            "2025-12-20", "09.00-10.00", 25000);

      boolean result = bookingService.addBooking(booking);
      assertTrue(result, "Booking should be added successfully");
   }

   @Test
   @Order(2)
   public void testGetAllBookings() {
      List<Booking> bookings = bookingService.getAllBookings();
      assertNotNull(bookings, "Bookings list should not be null");
      assertTrue(bookings.size() > 0, "Should have at least one booking");
   }

   @Test
   @Order(3)
   public void testCalculateSubtotal() {
      List<Booking> bookings = bookingService.getAllBookings();
      double subtotal = bookingService.calculateSubtotal(bookings);
      assertTrue(subtotal > 0, "Subtotal should be greater than 0");
   }

   @Test
   @Order(4)
   public void testCalculateTax() {
      List<Booking> bookings = bookingService.getAllBookings();
      double subtotal = bookingService.calculateSubtotal(bookings);
      double tax = bookingService.calculateTax(subtotal);

      assertEquals(subtotal * 0.12, tax, 0.01, "Tax should be 12% of subtotal");
   }

   @Test
   @Order(5)
   public void testCalculateTotal() {
      List<Booking> bookings = bookingService.getAllBookings();
      double subtotal = bookingService.calculateSubtotal(bookings);
      double tax = bookingService.calculateTax(subtotal);
      double total = bookingService.calculateTotal(subtotal, tax);

      assertEquals(subtotal + tax, total, 0.01, "Total should be subtotal + tax");
   }
}
