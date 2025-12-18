package com.courtee.database;

import com.courtee.database.dao.*;
import com.courtee.model.*;

import java.sql.SQLException;
import java.util.Arrays;

public class DatabaseInitializer {

   public static void initializeData() {
      try {
         VenueDAO venueDAO = new VenueDAO();
         CourtDAO courtDAO = new CourtDAO();
         TimeSlotDAO timeSlotDAO = new TimeSlotDAO();

         // Check if data already exists
         if (!venueDAO.getAllVenues().isEmpty()) {
            System.out.println("Database already initialized.");
            return;
         }

         System.out.println("Initializing database with sample data...");

         // Insert Venues
         Venue v1 = new Venue("longfield-sport-center", "Longfield Sport Center",
               "Lapangan Sepak Bola", "Jakarta Barat", 25000, "soccer-field.jpg");
         venueDAO.insertVenue(v1);
         for (String slot : Arrays.asList("07.00-08.00", "08.00-09.00", "10.00-11.00", "13.00-14.00", "14.00-15.00")) {
            venueDAO.insertVenueTimeSlot(v1.getId(), slot);
         }

         Venue v2 = new Venue("culture-padel", "Culture Padel",
               "Lapangan Padel", "Jakarta Timur", 40000, "padel-court.jpg");
         venueDAO.insertVenue(v2);
         for (String slot : Arrays.asList("05.00-06.00", "08.00-09.00", "10.00-11.00", "13.00-14.00")) {
            venueDAO.insertVenueTimeSlot(v2.getId(), slot);
         }

         Venue v3 = new Venue("balistic-badminton", "Balistic Badminton",
               "Lapangan Badminton", "Jawa Timur", 35000, "badminton-court.jpg");
         venueDAO.insertVenue(v3);
         for (String slot : Arrays.asList("07.00-08.00", "08.00-09.00", "10.00-11.00", "13.00-14.00", "14.00-15.00")) {
            venueDAO.insertVenueTimeSlot(v3.getId(), slot);
         }

         Venue v4 = new Venue("sumber-sari-jaya", "Sumber Sari Jaya",
               "Lapangan Mini Soccer", "Singosari", 25000, "mini-soccer.jpg");
         venueDAO.insertVenue(v4);
         for (String slot : Arrays.asList("07.00-08.00", "08.00-09.00", "10.00-11.00", "13.00-14.00", "14.00-15.00")) {
            venueDAO.insertVenueTimeSlot(v4.getId(), slot);
         }

         Venue v5 = new Venue("singhasari-tennis-club", "Singhasari Tennis Club",
               "Lapangan Tennis", "Jawa Barat", 40000, "tennis-court.jpg");
         venueDAO.insertVenue(v5);
         for (String slot : Arrays.asList("05.00-06.00", "09.00-10.00", "10.00-11.00", "13.00-14.00")) {
            venueDAO.insertVenueTimeSlot(v5.getId(), slot);
         }

         Venue v6 = new Venue("balistic-badminton-2", "Balistic Badminton",
               "Lapangan Badminton", "Jawa Tengah", 35000, "badminton-court-2.jpg");
         venueDAO.insertVenue(v6);
         for (String slot : Arrays.asList("07.00-08.00", "08.00-09.00", "10.00-11.00", "13.00-14.00", "14.00-15.00")) {
            venueDAO.insertVenueTimeSlot(v6.getId(), slot);
         }

         // Insert Courts for each venue
         insertCourtsForLongfield(courtDAO, timeSlotDAO);
         insertCourtsForCulturePadel(courtDAO, timeSlotDAO);
         insertCourtsForBalisticBadminton(courtDAO, timeSlotDAO);
         insertCourtsForSumberSariJaya(courtDAO, timeSlotDAO);
         insertCourtsForSinghasariTennis(courtDAO, timeSlotDAO);
         insertCourtsForBalisticBadminton2(courtDAO, timeSlotDAO);

         System.out.println("Database initialization completed successfully!");

      } catch (SQLException e) {
         System.err.println("Error initializing database: " + e.getMessage());
         e.printStackTrace();
      }
   }

   private static void insertCourtsForLongfield(CourtDAO courtDAO, TimeSlotDAO timeSlotDAO) throws SQLException {
      Court court1 = new Court("lapangan-sejahtera", "Lapangan Sejahtera",
            "Fasilitas berkualitas dengan penerangan malam dan area bersih. Cocok untuk latihan, pertandingan persahabatan, serta kegiatan komunitas.",
            "indoor-soccer-field.jpg");
      courtDAO.insertCourt(court1, "longfield-sport-center");
      insertTimeSlots(timeSlotDAO, court1.getId(), 24000);

      Court court2 = new Court("lapangan-makmur", "Lapangan Makmur",
            "Fasilitas berkualitas dengan penerangan malam dan area bersih. Cocok untuk latihan, pertandingan persahabatan, serta kegiatan komunitas.",
            "indoor-futsal-court.jpg");
      courtDAO.insertCourt(court2, "longfield-sport-center");
      insertTimeSlots(timeSlotDAO, court2.getId(), 25000);
   }

   private static void insertCourtsForCulturePadel(CourtDAO courtDAO, TimeSlotDAO timeSlotDAO) throws SQLException {
      Court court1 = new Court("court-1", "Court Premium 1",
            "Fasilitas berkualitas dengan penerangan malam dan area bersih. Cocok untuk latihan, pertandingan persahabatan, serta kegiatan komunitas.",
            "padel-court-1.jpg");
      courtDAO.insertCourt(court1, "culture-padel");
      insertTimeSlots(timeSlotDAO, court1.getId(), 40000);

      Court court2 = new Court("court-2", "Court Premium 2",
            "Fasilitas berkualitas dengan penerangan malam dan area bersih. Cocok untuk latihan, pertandingan persahabatan, serta kegiatan komunitas.",
            "padel-court-2.jpg");
      courtDAO.insertCourt(court2, "culture-padel");
      insertTimeSlots(timeSlotDAO, court2.getId(), 40000);
   }

   private static void insertCourtsForBalisticBadminton(CourtDAO courtDAO, TimeSlotDAO timeSlotDAO)
         throws SQLException {
      Court court1 = new Court("court-a", "Court A",
            "Fasilitas berkualitas dengan penerangan malam dan area bersih. Cocok untuk latihan, pertandingan persahabatan, serta kegiatan komunitas.",
            "badminton-court-a.jpg");
      courtDAO.insertCourt(court1, "balistic-badminton");
      insertTimeSlots(timeSlotDAO, court1.getId(), 35000);

      Court court2 = new Court("court-b", "Court B",
            "Fasilitas berkualitas dengan penerangan malam dan area bersih. Cocok untuk latihan, pertandingan persahabatan, serta kegiatan komunitas.",
            "badminton-court-b.jpg");
      courtDAO.insertCourt(court2, "balistic-badminton");
      insertTimeSlots(timeSlotDAO, court2.getId(), 35000);
   }

   private static void insertCourtsForSumberSariJaya(CourtDAO courtDAO, TimeSlotDAO timeSlotDAO) throws SQLException {
      Court court1 = new Court("lapangan-1", "Lapangan Mini Soccer 1",
            "Fasilitas berkualitas dengan penerangan malam dan area bersih. Cocok untuk latihan, pertandingan persahabatan, serta kegiatan komunitas.",
            "mini-soccer-1.jpg");
      courtDAO.insertCourt(court1, "sumber-sari-jaya");
      insertTimeSlots(timeSlotDAO, court1.getId(), 25000);

      Court court2 = new Court("lapangan-2", "Lapangan Mini Soccer 2",
            "Fasilitas berkualitas dengan penerangan malam dan area bersih. Cocok untuk latihan, pertandingan persahabatan, serta kegiatan komunitas.",
            "mini-soccer-2.jpg");
      courtDAO.insertCourt(court2, "sumber-sari-jaya");
      insertTimeSlots(timeSlotDAO, court2.getId(), 25000);
   }

   private static void insertCourtsForSinghasariTennis(CourtDAO courtDAO, TimeSlotDAO timeSlotDAO) throws SQLException {
      Court court1 = new Court("tennis-1", "Tennis Court 1",
            "Fasilitas berkualitas dengan penerangan malam dan area bersih. Cocok untuk latihan, pertandingan persahabatan, serta kegiatan komunitas.",
            "tennis-court-1.jpg");
      courtDAO.insertCourt(court1, "singhasari-tennis-club");
      insertTimeSlots(timeSlotDAO, court1.getId(), 40000);

      Court court2 = new Court("tennis-2", "Tennis Court 2",
            "Fasilitas berkualitas dengan penerangan malam dan area bersih. Cocok untuk latihan, pertandingan persahabatan, serta kegiatan komunitas.",
            "tennis-court-2.jpg");
      courtDAO.insertCourt(court2, "singhasari-tennis-club");
      insertTimeSlots(timeSlotDAO, court2.getId(), 40000);
   }

   private static void insertCourtsForBalisticBadminton2(CourtDAO courtDAO, TimeSlotDAO timeSlotDAO)
         throws SQLException {
      Court court1 = new Court("court-c", "Court C",
            "Fasilitas berkualitas dengan penerangan malam dan area bersih. Cocok untuk latihan, pertandingan persahabatan, serta kegiatan komunitas.",
            "badminton-court-c.jpg");
      courtDAO.insertCourt(court1, "balistic-badminton-2");
      insertTimeSlots(timeSlotDAO, court1.getId(), 35000);

      Court court2 = new Court("court-d", "Court D",
            "Fasilitas berkualitas dengan penerangan malam dan area bersih. Cocok untuk latihan, pertandingan persahabatan, serta kegiatan komunitas.",
            "badminton-court-d.jpg");
      courtDAO.insertCourt(court2, "balistic-badminton-2");
      insertTimeSlots(timeSlotDAO, court2.getId(), 35000);
   }

   private static void insertTimeSlots(TimeSlotDAO timeSlotDAO, String courtId, double basePrice) throws SQLException {
      timeSlotDAO.insertTimeSlot(courtId, new TimeSlot("06.00-07.00", basePrice, true));
      timeSlotDAO.insertTimeSlot(courtId, new TimeSlot("07.00-08.00", basePrice, true));
      timeSlotDAO.insertTimeSlot(courtId, new TimeSlot("09.00-10.00", basePrice, true));
      timeSlotDAO.insertTimeSlot(courtId, new TimeSlot("10.00-11.00", basePrice, false));
      timeSlotDAO.insertTimeSlot(courtId, new TimeSlot("11.00-12.00", basePrice, false));
      timeSlotDAO.insertTimeSlot(courtId, new TimeSlot("13.00-14.00", basePrice, false));
      timeSlotDAO.insertTimeSlot(courtId, new TimeSlot("14.00-15.00", basePrice, true));
      timeSlotDAO.insertTimeSlot(courtId, new TimeSlot("16.00-17.00", basePrice, true));
   }
}
