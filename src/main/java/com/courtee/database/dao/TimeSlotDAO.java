package com.courtee.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.courtee.database.DatabaseConnection;
import com.courtee.model.TimeSlot;

public class TimeSlotDAO {

   public void insertTimeSlot(String courtId, TimeSlot timeSlot) throws SQLException {
      String sql = "INSERT INTO time_slots (court_id, time, price, available) VALUES (?, ?, ?, ?)";
      try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
         pstmt.setString(1, courtId);
         pstmt.setString(2, timeSlot.getTime());
         pstmt.setDouble(3, timeSlot.getPrice());
         pstmt.setInt(4, timeSlot.isAvailable() ? 1 : 0);
         pstmt.executeUpdate();
      }
   }

   public void updateAvailability(String courtId, String time, boolean available) throws SQLException {
      String sql = "UPDATE time_slots SET available = ? WHERE court_id = ? AND time = ?";
      try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
         pstmt.setInt(1, available ? 1 : 0);
         pstmt.setString(2, courtId);
         pstmt.setString(3, time);
         pstmt.executeUpdate();
      }
   }

   /**
    * Check if a time slot is available for a specific date by checking bookings
    * 
    * @param courtId The court ID
    * @param time    The time slot
    * @param date    The date to check
    * @return true if available, false if already booked
    */
   public boolean isAvailableForDate(String courtId, String time, String date) throws SQLException {
      String sql = "SELECT COUNT(*) as count FROM bookings WHERE court_name IN " +
            "(SELECT name FROM courts WHERE court_id = ?) " +
            "AND time_slot = ? AND booking_date = ?";
      try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
         pstmt.setString(1, courtId);
         pstmt.setString(2, time);
         pstmt.setString(3, date);
         ResultSet rs = pstmt.executeQuery();

         if (rs.next()) {
            return rs.getInt("count") == 0; // Available if no bookings found
         }
      }
      return true; // Default to available if query fails
   }

   public boolean isAvailable(String courtId, String time) throws SQLException {
      String sql = "SELECT available FROM time_slots WHERE court_id = ? AND time = ?";
      try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
         pstmt.setString(1, courtId);
         pstmt.setString(2, time);
         ResultSet rs = pstmt.executeQuery();

         if (rs.next()) {
            return rs.getInt("available") == 1;
         }
      }
      return false;
   }
}
