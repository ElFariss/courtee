package com.courtee.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.courtee.database.DatabaseConnection;
import com.courtee.model.Booking;

public class BookingDAO {

   public int insertBooking(Booking booking) throws SQLException {
      String sql = "INSERT INTO bookings (venue_name, court_name, booking_date, time_slot, price, payment_method, payment_status) "
            +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";
      try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
         pstmt.setString(1, booking.getVenueName());
         pstmt.setString(2, booking.getCourtName());
         pstmt.setString(3, booking.getDate());
         pstmt.setString(4, booking.getTime());
         pstmt.setDouble(5, booking.getPrice());
         pstmt.setString(6, "PENDING"); // payment method
         pstmt.setString(7, "PENDING"); // payment status

         int affectedRows = pstmt.executeUpdate();

         if (affectedRows > 0) {
            // Use SQLite's last_insert_rowid() function to get the generated ID
            try (Statement stmt = conn.createStatement();
                  ResultSet rs = stmt.executeQuery("SELECT last_insert_rowid()")) {
               if (rs.next()) {
                  return rs.getInt(1);
               }
            }
         }
      }
      return -1;
   }

   public List<Booking> getAllBookings() throws SQLException {
      List<Booking> bookings = new ArrayList<>();
      String sql = "SELECT * FROM bookings ORDER BY created_at DESC";

      try (Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

         while (rs.next()) {
            Booking booking = new Booking(
                  rs.getString("venue_name"),
                  rs.getString("court_name"),
                  rs.getString("booking_date"),
                  rs.getString("time_slot"),
                  rs.getDouble("price"));
            bookings.add(booking);
         }
      }
      return bookings;
   }

   public void updatePaymentStatus(int bookingId, String paymentMethod, String paymentStatus) throws SQLException {
      String sql = "UPDATE bookings SET payment_method = ?, payment_status = ? WHERE id = ?";
      try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
         pstmt.setString(1, paymentMethod);
         pstmt.setString(2, paymentStatus);
         pstmt.setInt(3, bookingId);
         pstmt.executeUpdate();
      }
   }

   public boolean deleteBooking(int bookingId) throws SQLException {
      String sql = "DELETE FROM bookings WHERE id = ?";
      try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
         pstmt.setInt(1, bookingId);
         return pstmt.executeUpdate() > 0;
      }
   }
}
