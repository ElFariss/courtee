package com.courtee.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.courtee.database.dao.BookingDAO;
import com.courtee.database.dao.TimeSlotDAO;
import com.courtee.model.Booking;
import com.courtee.model.TimeSlot;

public class BookingService implements IBookingService {

   private final BookingDAO bookingDAO;
   private final TimeSlotDAO timeSlotDAO;

   public BookingService() {
      this.bookingDAO = new BookingDAO();
      this.timeSlotDAO = new TimeSlotDAO();
   }

   @Override
   public Booking createBooking(String venueName, String courtName, String date, TimeSlot timeSlot) {
      Booking booking = new Booking(venueName, courtName, date, timeSlot.getTime(), timeSlot.getPrice());
      try {
         int bookingId = bookingDAO.insertBooking(booking);
         if (bookingId > 0) {
            return booking;
         }
      } catch (SQLException e) {
         System.err.println("Error creating booking: " + e.getMessage());
      }
      return null;
   }

   @Override
   public boolean cancelBooking(Booking booking) {
      // Note: We'd need to add a booking ID to the Booking model to properly
      // implement this
      // For now, this is a placeholder
      return false;
   }

   @Override
   public List<Booking> getUserBookings(String userId) {
      try {
         return bookingDAO.getAllBookings();
      } catch (SQLException e) {
         System.err.println("Error fetching bookings: " + e.getMessage());
         return new ArrayList<>();
      }
   }

   // Helper methods for testing and checkout
   public boolean addBooking(Booking booking) {
      try {
         int id = bookingDAO.insertBooking(booking);
         if (id > 0) {
            booking.setId(id);
            return true;
         }
      } catch (SQLException e) {
         System.err.println("Error adding booking: " + e.getMessage());
      }
      return false;
   }

   public List<Booking> getAllBookings() {
      try {
         return bookingDAO.getAllBookings();
      } catch (SQLException e) {
         System.err.println("Error fetching all bookings: " + e.getMessage());
         return new ArrayList<>();
      }
   }

   public double calculateSubtotal(List<Booking> bookings) {
      return bookings.stream()
            .mapToDouble(Booking::getPrice)
            .sum();
   }

   public double calculateTax(double subtotal) {
      return subtotal * 0.12; // 12% tax
   }

   public double calculateTotal(double subtotal, double tax) {
      return subtotal + tax;
   }

   /**
    * Check if a time slot is available for a specific court, time, and date
    * 
    * @param courtId Court ID
    * @param time    Time slot
    * @param date    Date to check
    * @return true if available
    */
   public boolean isSlotAvailable(String courtId, String time, String date) {
      try {
         return timeSlotDAO.isAvailableForDate(courtId, time, date);
      } catch (SQLException e) {
         System.err.println("Error checking slot availability: " + e.getMessage());
         return true; // Default to available if error
      }
   }

   /**
    * Confirm bookings - saves to database for date-specific tracking
    * Time slots remain globally available but are checked against bookings per
    * date
    * 
    * @param bookings List of bookings to confirm
    * @return true if all bookings were confirmed successfully
    */
   public boolean confirmBookings(List<Booking> bookings) {
      try {
         for (Booking booking : bookings) {
            // Insert the booking - this creates a date-specific reservation
            int bookingId = bookingDAO.insertBooking(booking);
            if (bookingId > 0) {
               booking.setId(bookingId);
               // Note: We do NOT mark time_slots as unavailable globally
               // Availability is checked per date by querying bookings table
            } else {
               return false;
            }
         }
         return true;
      } catch (SQLException e) {
         System.err.println("Error confirming bookings: " + e.getMessage());
         e.printStackTrace();
         return false;
      }
   }
}
