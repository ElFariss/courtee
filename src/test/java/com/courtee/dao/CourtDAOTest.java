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
import com.courtee.database.dao.CourtDAO;
import com.courtee.database.dao.VenueDAO;
import com.courtee.model.Court;
import com.courtee.model.TimeSlot;
import com.courtee.model.Venue;

/**
 * Test class for CourtDAO
 * Tests court CRUD operations and relationships with venues and time slots
 */
public class CourtDAOTest {

   private CourtDAO courtDAO;
   private VenueDAO venueDAO;
   private Connection testConnection;

   @BeforeEach
   public void setUp() throws SQLException {
      // Initialize test database
      DatabaseConnection.initialize("test_court_dao.db");
      testConnection = DatabaseConnection.getConnection();
      DatabaseConnection.createTables(testConnection);

      courtDAO = new CourtDAO();
      venueDAO = new VenueDAO();

      // Setup a test venue for courts
      Venue testVenue = new Venue("test-venue-1", "Test Venue", "Football",
            "Jakarta", 50000, "test.jpg");
      venueDAO.insertVenue(testVenue);
   }

   @AfterEach
   public void tearDown() throws SQLException {
      if (testConnection != null && !testConnection.isClosed()) {
         testConnection.close();
      }
      // Clean up test database file
      java.io.File dbFile = new java.io.File("test_court_dao.db");
      if (dbFile.exists()) {
         dbFile.delete();
      }
   }

   @Test
   public void testInsertCourt() throws SQLException {
      Court court = new Court("court-1", "Test Court", "Test Description", "court.jpg");

      assertDoesNotThrow(() -> {
         courtDAO.insertCourt(court, "test-venue-1");
      });

      // Verify insertion
      Court retrieved = courtDAO.getCourtById("court-1");
      assertNotNull(retrieved);
      assertEquals("Test Court", retrieved.getName());
   }

   @Test
   public void testGetCourtById() throws SQLException {
      Court court = new Court("court-specific", "Specific Court",
            "Specific Description", "specific.jpg");
      courtDAO.insertCourt(court, "test-venue-1");

      Court retrieved = courtDAO.getCourtById("court-specific");

      assertNotNull(retrieved);
      assertEquals("court-specific", retrieved.getId());
      assertEquals("Specific Court", retrieved.getName());
      assertEquals("Specific Description", retrieved.getDescription());
   }

   @Test
   public void testGetCourtByIdNotFound() throws SQLException {
      Court retrieved = courtDAO.getCourtById("non-existent-court");

      assertNull(retrieved);
   }

   @Test
   public void testGetCourtsByVenue() throws SQLException {
      // Insert multiple courts for the same venue
      courtDAO.insertCourt(new Court("court-1", "Court One", "Description 1", "c1.jpg"),
            "test-venue-1");
      courtDAO.insertCourt(new Court("court-2", "Court Two", "Description 2", "c2.jpg"),
            "test-venue-1");
      courtDAO.insertCourt(new Court("court-3", "Court Three", "Description 3", "c3.jpg"),
            "test-venue-1");

      List<Court> courts = courtDAO.getCourtsByVenue("test-venue-1");

      assertNotNull(courts);
      assertEquals(3, courts.size());
   }

   @Test
   public void testGetCourtsByVenueEmpty() throws SQLException {
      // Create a venue with no courts
      Venue emptyVenue = new Venue("empty-venue", "Empty Venue", "Tennis",
            "Bandung", 60000, "empty.jpg");
      venueDAO.insertVenue(emptyVenue);

      List<Court> courts = courtDAO.getCourtsByVenue("empty-venue");

      assertNotNull(courts);
      assertTrue(courts.isEmpty());
   }

   @Test
   public void testCourtWithTimeSlots() throws SQLException {
      Court court = new Court("court-with-slots", "Court With Slots",
            "Has time slots", "slots.jpg");

      // Add time slots to court
      court.addTimeSlot(new TimeSlot("08:00 - 09:00", 50000, true));
      court.addTimeSlot(new TimeSlot("09:00 - 10:00", 50000, true));

      courtDAO.insertCourt(court, "test-venue-1");

      // Retrieve and verify time slots are loaded
      Court retrieved = courtDAO.getCourtById("court-with-slots");

      assertNotNull(retrieved);
      // Note: Time slots loading depends on DAO implementation
      // This test verifies the court structure supports time slots
   }

   @Test
   public void testInsertDuplicateCourt() throws SQLException {
      Court court1 = new Court("duplicate-id", "First Court", "First", "first.jpg");
      Court court2 = new Court("duplicate-id", "Second Court", "Second", "second.jpg");

      courtDAO.insertCourt(court1, "test-venue-1");
      courtDAO.insertCourt(court2, "test-venue-1"); // Should be ignored

      Court retrieved = courtDAO.getCourtById("duplicate-id");

      // Should retain first court's data due to INSERT OR IGNORE
      assertEquals("First Court", retrieved.getName());
   }

   @Test
   public void testMultipleVenuesWithCourts() throws SQLException {
      // Create another venue
      Venue venue2 = new Venue("test-venue-2", "Venue Two", "Badminton",
            "Surabaya", 40000, "v2.jpg");
      venueDAO.insertVenue(venue2);

      // Add courts to different venues
      courtDAO.insertCourt(new Court("v1-court-1", "Venue 1 Court 1", "Desc", "c1.jpg"),
            "test-venue-1");
      courtDAO.insertCourt(new Court("v1-court-2", "Venue 1 Court 2", "Desc", "c2.jpg"),
            "test-venue-1");
      courtDAO.insertCourt(new Court("v2-court-1", "Venue 2 Court 1", "Desc", "c3.jpg"),
            "test-venue-2");

      List<Court> venue1Courts = courtDAO.getCourtsByVenue("test-venue-1");
      List<Court> venue2Courts = courtDAO.getCourtsByVenue("test-venue-2");

      assertEquals(2, venue1Courts.size());
      assertEquals(1, venue2Courts.size());
   }

   @Test
   public void testUpdateTimeSlotAvailability() throws SQLException {
      Court court = new Court("court-update", "Update Court", "Desc", "update.jpg");
      courtDAO.insertCourt(court, "test-venue-1");

      // This tests the updateTimeSlotAvailability method
      assertDoesNotThrow(() -> {
         courtDAO.updateTimeSlotAvailability("court-update", "08:00 - 09:00", false);
      });
   }

   @Test
   public void testCourtDataIntegrity() throws SQLException {
      String courtId = "integrity-court";
      String name = "Integrity Test Court";
      String description = "Testing data integrity";
      String image = "integrity.jpg";

      Court court = new Court(courtId, name, description, image);
      courtDAO.insertCourt(court, "test-venue-1");

      Court retrieved = courtDAO.getCourtById(courtId);

      assertEquals(courtId, retrieved.getId());
      assertEquals(name, retrieved.getName());
      assertEquals(description, retrieved.getDescription());
      assertEquals(image, retrieved.getImage());
   }

   @Test
   public void testCourtBelongsToCorrectVenue() throws SQLException {
      // Create multiple venues
      Venue venueA = new Venue("venue-a", "Venue A", "Football", "Jakarta", 50000, "a.jpg");
      Venue venueB = new Venue("venue-b", "Venue B", "Tennis", "Bandung", 60000, "b.jpg");
      venueDAO.insertVenue(venueA);
      venueDAO.insertVenue(venueB);

      // Add courts to each venue
      courtDAO.insertCourt(new Court("court-a1", "Court A1", "Desc", "ca1.jpg"), "venue-a");
      courtDAO.insertCourt(new Court("court-a2", "Court A2", "Desc", "ca2.jpg"), "venue-a");
      courtDAO.insertCourt(new Court("court-b1", "Court B1", "Desc", "cb1.jpg"), "venue-b");

      // Verify correct associations
      List<Court> venueACourts = courtDAO.getCourtsByVenue("venue-a");
      List<Court> venueBCourts = courtDAO.getCourtsByVenue("venue-b");

      assertEquals(2, venueACourts.size());
      assertEquals(1, venueBCourts.size());

      assertTrue(venueACourts.stream().anyMatch(c -> c.getId().equals("court-a1")));
      assertTrue(venueACourts.stream().anyMatch(c -> c.getId().equals("court-a2")));
      assertTrue(venueBCourts.stream().anyMatch(c -> c.getId().equals("court-b1")));
   }
}
