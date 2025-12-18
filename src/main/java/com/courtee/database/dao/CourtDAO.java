package com.courtee.database.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.courtee.database.DatabaseConnection;
import com.courtee.model.Court;
import com.courtee.model.TimeSlot;

public class CourtDAO {

   public void insertCourt(Court court, String venueId) throws SQLException {
      String sql = "INSERT OR IGNORE INTO courts (court_id, venue_id, name, description, image_name, image_data) VALUES (?, ?, ?, ?, ?, ?)";
      try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
         pstmt.setString(1, court.getId());
         pstmt.setString(2, venueId);
         pstmt.setString(3, court.getName());
         pstmt.setString(4, court.getDescription());
         pstmt.setString(5, court.getImage());

         // Load image from resources and store as BLOB
         byte[] imageData = loadImageFromResources(court.getImage());
         if (imageData != null) {
            pstmt.setBytes(6, imageData);
         } else {
            pstmt.setNull(6, Types.BLOB);
         }
         pstmt.executeUpdate();
      }
   }

   private byte[] loadImageFromResources(String imageName) {
      try (InputStream is = getClass().getResourceAsStream("/" + imageName)) {
         if (is != null) {
            return is.readAllBytes();
         }
      } catch (IOException e) {
         System.err.println("Failed to load image: " + imageName + " - " + e.getMessage());
      }
      return null;
   }

   public List<Court> getCourtsByVenue(String venueId) throws SQLException {
      List<Court> courts = new ArrayList<>();
      String sql = "SELECT court_id, name, description, image_name, image_data FROM courts WHERE venue_id = ?";

      try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
         pstmt.setString(1, venueId);
         try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
               Court court = new Court(
                     rs.getString("court_id"),
                     rs.getString("name"),
                     rs.getString("description"),
                     rs.getString("image_name"));

               // Read BLOB data safely
               byte[] imageData = rs.getBytes("image_data");
               if (imageData != null && imageData.length > 0) {
                  court.setImageData(imageData);
               }

               courts.add(court);
            }
         }
      }

      // Load time slots after the main query completes
      for (Court court : courts) {
         court.setTimeSlots(getTimeSlotsByCourt(court.getId()));
      }

      return courts;
   }

   public Court getCourtById(String courtId) throws SQLException {
      String sql = "SELECT court_id, name, description, image_name, image_data FROM courts WHERE court_id = ?";

      try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
         pstmt.setString(1, courtId);
         try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
               Court court = new Court(
                     rs.getString("court_id"),
                     rs.getString("name"),
                     rs.getString("description"),
                     rs.getString("image_name"));

               // Read BLOB data safely
               byte[] imageData = rs.getBytes("image_data");
               if (imageData != null && imageData.length > 0) {
                  court.setImageData(imageData);
               }

               court.setTimeSlots(getTimeSlotsByCourt(courtId));
               return court;
            }
         }
      }
      return null;
   }

   private List<TimeSlot> getTimeSlotsByCourt(String courtId) throws SQLException {
      List<TimeSlot> timeSlots = new ArrayList<>();
      String sql = "SELECT * FROM time_slots WHERE court_id = ?";

      try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
         pstmt.setString(1, courtId);
         try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
               TimeSlot slot = new TimeSlot(
                     rs.getString("time"),
                     rs.getDouble("price"),
                     rs.getInt("available") == 1);
               timeSlots.add(slot);
            }
         }
      }
      return timeSlots;
   }

   public void updateTimeSlotAvailability(String courtId, String time, boolean available) throws SQLException {
      String sql = "UPDATE time_slots SET available = ? WHERE court_id = ? AND time = ?";
      try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
         pstmt.setInt(1, available ? 1 : 0);
         pstmt.setString(2, courtId);
         pstmt.setString(3, time);
         pstmt.executeUpdate();
      }
   }
}
