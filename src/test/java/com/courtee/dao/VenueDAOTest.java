package com.courtee.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.courtee.database.DatabaseConnection;
import com.courtee.database.dao.VenueDAO;
import com.courtee.model.Venue;

/**
 * Test class for VenueDAO
 * Tests CRUD operations and search functionality for venues
 */
public class VenueDAOTest {

   private VenueDAO venueDAO;
   private Connection testConnection;

   @BeforeEach
   public void setUp() throws SQLException {
      // Initialize test database with a unique name
      DatabaseConnection.initialize("test_venue_dao.db");
      testConnection = DatabaseConnection.getConnection();
      DatabaseConnection.createTables(testConnection);

      venueDAO = new VenueDAO();
   }

   @AfterEach
   public void tearDown() throws SQLException {
      if (testConnection != null && !testConnection.isClosed()) {
         testConnection.close();
      }
      // Clean up test database file
      java.io.File dbFile = new java.io.File("test_venue_dao.db");
      if (dbFile.exists()) {
         dbFile.delete();
      }
   }

   @Test
   public void testInsertVenue() throws SQLException {
      Venue venue = new Venue("test-venue-1", "Test Venue", "Football",
            "Jakarta", 50000, "test-image.jpg");

      assertDoesNotThrow(() -> venueDAO.insertVenue(venue));

      // Verify insertion
      Venue retrieved = venueDAO.getVenueById("test-venue-1");
      assertNotNull(retrieved);
      assertEquals("Test Venue", retrieved.getName());
      assertEquals("Jakarta", retrieved.getLocation());
   }

   @Test
   public void testGetAllVenues() throws SQLException {
      // Insert multiple venues
      venueDAO.insertVenue(new Venue("venue-1", "Venue One", "Football",
            "Jakarta", 50000, "image1.jpg"));
      venueDAO.insertVenue(new Venue("venue-2", "Venue Two", "Badminton",
            "Bandung", 40000, "image2.jpg"));
      venueDAO.insertVenue(new Venue("venue-3", "Venue Three", "Tennis",
            "Surabaya", 60000, "image3.jpg"));

      List<Venue> venues = venueDAO.getAllVenues();

      assertNotNull(venues);
      assertEquals(3, venues.size());
   }

   @Test
   public void testGetVenueById() throws SQLException {
      Venue venue = new Venue("specific-venue", "Specific Venue", "Basketball",
            "Medan", 45000, "specific.jpg");
      venueDAO.insertVenue(venue);

      Venue retrieved = venueDAO.getVenueById("specific-venue");

      assertNotNull(retrieved);
      assertEquals("Specific Venue", retrieved.getName());
      assertEquals("Basketball", retrieved.getType());
      assertEquals("Medan", retrieved.getLocation());
      assertEquals(45000, retrieved.getPricePerHour());
   }

   @Test
   public void testGetVenueByIdNotFound() throws SQLException {
      Venue retrieved = venueDAO.getVenueById("non-existent-venue");

      assertNull(retrieved);
   }

   @Test
   public void testSearchVenuesByLocation() throws SQLException {
      venueDAO.insertVenue(new Venue("venue-jkt-1", "Jakarta Venue 1", "Football",
            "Jakarta", 50000, "img1.jpg"));
      venueDAO.insertVenue(new Venue("venue-jkt-2", "Jakarta Venue 2", "Badminton",
            "Jakarta", 40000, "img2.jpg"));
      venueDAO.insertVenue(new Venue("venue-bdg-1", "Bandung Venue", "Tennis",
            "Bandung", 60000, "img3.jpg"));

      List<Venue> jakartaVenues = venueDAO.searchVenuesByLocation("Jakarta");

      assertNotNull(jakartaVenues);
      assertEquals(2, jakartaVenues.size());
      assertTrue(jakartaVenues.stream().allMatch(v -> v.getLocation().equals("Jakarta")));
   }

   @Test
   public void testSearchVenuesByType() throws SQLException {
      venueDAO.insertVenue(new Venue("venue-fb-1", "Football Venue 1", "Football",
            "Jakarta", 50000, "img1.jpg"));
      venueDAO.insertVenue(new Venue("venue-fb-2", "Football Venue 2", "Football",
            "Bandung", 55000, "img2.jpg"));
      venueDAO.insertVenue(new Venue("venue-bd-1", "Badminton Venue", "Badminton",
            "Surabaya", 40000, "img3.jpg"));

      List<Venue> footballVenues = venueDAO.searchVenuesByType("Football");

      assertNotNull(footballVenues);
      assertEquals(2, footballVenues.size());
      assertTrue(footballVenues.stream().allMatch(v -> v.getType().equals("Football")));
   }

   @Test
   public void testInsertVenueTimeSlot() throws SQLException {
      Venue venue = new Venue("venue-ts", "Venue with TimeSlots", "Football",
            "Jakarta", 50000, "img.jpg");
      venueDAO.insertVenue(venue);

      assertDoesNotThrow(() -> {
         venueDAO.insertVenueTimeSlot("venue-ts", "08:00 - 09:00");
         venueDAO.insertVenueTimeSlot("venue-ts", "09:00 - 10:00");
      });

      List<String> timeSlots = venueDAO.getVenueTimeSlots("venue-ts");

      assertNotNull(timeSlots);
      assertEquals(2, timeSlots.size());
      assertTrue(timeSlots.contains("08:00 - 09:00"));
      assertTrue(timeSlots.contains("09:00 - 10:00"));
   }

   @Test
   public void testGetVenueTimeSlotsEmpty() throws SQLException {
      Venue venue = new Venue("venue-no-ts", "Venue No TimeSlots", "Football",
            "Jakarta", 50000, "img.jpg");
      venueDAO.insertVenue(venue);

      List<String> timeSlots = venueDAO.getVenueTimeSlots("venue-no-ts");

      assertNotNull(timeSlots);
      assertTrue(timeSlots.isEmpty());
   }

   @Test
   public void testInsertDuplicateVenue() throws SQLException {
      Venue venue1 = new Venue("duplicate-id", "First Venue", "Football",
            "Jakarta", 50000, "img1.jpg");
      Venue venue2 = new Venue("duplicate-id", "Second Venue", "Badminton",
            "Bandung", 40000, "img2.jpg");

      venueDAO.insertVenue(venue1);
      venueDAO.insertVenue(venue2); // Should be ignored due to INSERT OR IGNORE

      Venue retrieved = venueDAO.getVenueById("duplicate-id");

      // Should still have the first venue's data
      assertEquals("First Venue", retrieved.getName());
      assertEquals("Football", retrieved.getType());
   }

   @Test
   public void testSearchVenuesByLocationWithWildcard() throws SQLException {
      venueDAO.insertVenue(new Venue("venue-1", "North Jakarta Venue", "Football",
            "North Jakarta", 50000, "img1.jpg"));
      venueDAO.insertVenue(new Venue("venue-2", "South Jakarta Venue", "Badminton",
            "South Jakarta", 40000, "img2.jpg"));
      venueDAO.insertVenue(new Venue("venue-3", "Bandung Venue", "Tennis",
            "Bandung", 60000, "img3.jpg"));

      // Search with partial match (LIKE query)
      List<Venue> jakartaVenues = venueDAO.searchVenuesByLocation("Jakarta");

      assertNotNull(jakartaVenues);
      assertEquals(2, jakartaVenues.size());
   }
}
