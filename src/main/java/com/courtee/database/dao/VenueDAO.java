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
import com.courtee.model.Venue;

public class VenueDAO {

   public void insertVenue(Venue venue) throws SQLException {
      String sql = "INSERT OR IGNORE INTO venues (venue_id, name, type, location, price_per_hour, image_name, image_data) VALUES (?, ?, ?, ?, ?, ?, ?)";
      try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
         pstmt.setString(1, venue.getId());
         pstmt.setString(2, venue.getName());
         pstmt.setString(3, venue.getType());
         pstmt.setString(4, venue.getLocation());
         pstmt.setDouble(5, venue.getPricePerHour());
         pstmt.setString(6, venue.getImage());

         // Load image from resources and store as BLOB
         byte[] imageData = loadImageFromResources(venue.getImage());
         if (imageData != null) {
            pstmt.setBytes(7, imageData);
         } else {
            pstmt.setNull(7, Types.BLOB);
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

   public void insertVenueTimeSlot(String venueId, String timeSlot) throws SQLException {
      String sql = "INSERT INTO venue_time_slots (venue_id, time_slot) VALUES (?, ?)";
      try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
         pstmt.setString(1, venueId);
         pstmt.setString(2, timeSlot);
         pstmt.executeUpdate();
      }
   }

   public List<Venue> getAllVenues() throws SQLException {
      List<Venue> venues = new ArrayList<>();
      String sql = "SELECT venue_id, name, type, location, price_per_hour, image_name, image_data FROM venues";

      try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()) {

         while (rs.next()) {
            Venue venue = new Venue(
                  rs.getString("venue_id"),
                  rs.getString("name"),
                  rs.getString("type"),
                  rs.getString("location"),
                  rs.getDouble("price_per_hour"),
                  rs.getString("image_name"));

            // Read BLOB data safely
            byte[] imageData = rs.getBytes("image_data");
            if (imageData != null && imageData.length > 0) {
               venue.setImageData(imageData);
            }

            venues.add(venue);
         }
      }

      // Load time slots after the main query completes (to avoid nested query issues)
      for (Venue venue : venues) {
         venue.setTimeSlots(getVenueTimeSlots(venue.getId()));
      }

      return venues;
   }

   public Venue getVenueById(String venueId) throws SQLException {
      String sql = "SELECT venue_id, name, type, location, price_per_hour, image_name, image_data FROM venues WHERE venue_id = ?";

      try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
         pstmt.setString(1, venueId);
         try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
               Venue venue = new Venue(
                     rs.getString("venue_id"),
                     rs.getString("name"),
                     rs.getString("type"),
                     rs.getString("location"),
                     rs.getDouble("price_per_hour"),
                     rs.getString("image_name"));

               // Read BLOB data safely
               byte[] imageData = rs.getBytes("image_data");
               if (imageData != null && imageData.length > 0) {
                  venue.setImageData(imageData);
               }

               venue.setTimeSlots(getVenueTimeSlots(venueId));
               return venue;
            }
         }
      }
      return null;
   }

   public List<String> getVenueTimeSlots(String venueId) throws SQLException {
      List<String> timeSlots = new ArrayList<>();
      String sql = "SELECT time_slot FROM venue_time_slots WHERE venue_id = ?";

      try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
         pstmt.setString(1, venueId);
         try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
               timeSlots.add(rs.getString("time_slot"));
            }
         }
      }
      return timeSlots;
   }

   public List<Venue> searchVenuesByLocation(String location) throws SQLException {
      List<Venue> venues = new ArrayList<>();
      String sql = "SELECT venue_id, name, type, location, price_per_hour, image_name, image_data FROM venues WHERE location LIKE ?";

      try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
         pstmt.setString(1, "%" + location + "%");
         try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
               Venue venue = new Venue(
                     rs.getString("venue_id"),
                     rs.getString("name"),
                     rs.getString("type"),
                     rs.getString("location"),
                     rs.getDouble("price_per_hour"),
                     rs.getString("image_name"));

               // Read BLOB data safely
               byte[] imageData = rs.getBytes("image_data");
               if (imageData != null && imageData.length > 0) {
                  venue.setImageData(imageData);
               }

               venues.add(venue);
            }
         }
      }

      // Load time slots after the main query completes
      for (Venue venue : venues) {
         venue.setTimeSlots(getVenueTimeSlots(venue.getId()));
      }

      return venues;
   }

   public List<Venue> searchVenuesByType(String type) throws SQLException {
      List<Venue> venues = new ArrayList<>();
      String sql = "SELECT venue_id, name, type, location, price_per_hour, image_name, image_data FROM venues WHERE type = ?";

      try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
         pstmt.setString(1, type);
         try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
               Venue venue = new Venue(
                     rs.getString("venue_id"),
                     rs.getString("name"),
                     rs.getString("type"),
                     rs.getString("location"),
                     rs.getDouble("price_per_hour"),
                     rs.getString("image_name"));

               // Read BLOB data safely
               byte[] imageData = rs.getBytes("image_data");
               if (imageData != null && imageData.length > 0) {
                  venue.setImageData(imageData);
               }

               venues.add(venue);
            }
         }
      }

      // Load time slots after the main query completes
      for (Venue venue : venues) {
         venue.setTimeSlots(getVenueTimeSlots(venue.getId()));
      }

      return venues;
   }
}
