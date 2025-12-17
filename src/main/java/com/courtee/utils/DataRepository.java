package com.courtee.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.courtee.model.Court;
import com.courtee.model.TimeSlot;
import com.courtee.model.Venue;

public class DataRepository {

   public static List<Venue> getVenues() {
      List<Venue> venues = new ArrayList<>();

      Venue v1 = new Venue("longfield-sport-center", "Longfield Sport Center",
            "Lapangan Sepak Bola", "Jakarta Barat", 25000, "soccer-field.jpg");
      v1.setTimeSlots(Arrays.asList("07.00-08.00", "08.00-09.00", "10.00-11.00", "13.00-14.00", "14.00-15.00"));
      venues.add(v1);

      Venue v2 = new Venue("culture-padel", "Culture Padel",
            "Lapangan Padel", "Jakarta Timur", 40000, "padel-court.jpg");
      v2.setTimeSlots(Arrays.asList("05.00-06.00", "08.00-09.00", "10.00-11.00", "13.00-14.00"));
      venues.add(v2);

      Venue v3 = new Venue("balistic-badminton", "Balistic Badminton",
            "Lapangan Badminton", "Jawa Timur", 35000, "badminton-court.jpg");
      v3.setTimeSlots(Arrays.asList("07.00-08.00", "08.00-09.00", "10.00-11.00", "13.00-14.00", "14.00-15.00"));
      venues.add(v3);

      Venue v4 = new Venue("sumber-sari-jaya", "Sumber Sari Jaya",
            "Lapangan Mini Soccer", "Singosari", 25000, "mini-soccer.jpg");
      v4.setTimeSlots(Arrays.asList("07.00-08.00", "08.00-09.00", "10.00-11.00", "13.00-14.00", "14.00-15.00"));
      venues.add(v4);

      Venue v5 = new Venue("singhasari-tennis-club", "Singhasari Tennis Club",
            "Lapangan Tennis", "Jawa Barat", 40000, "tennis-court.jpg");
      v5.setTimeSlots(Arrays.asList("05.00-06.00", "09.00-10.00", "10.00-11.00", "13.00-14.00"));
      venues.add(v5);

      Venue v6 = new Venue("balistic-badminton-2", "Balistic Badminton",
            "Lapangan Badminton", "Jawa Tengah", 35000, "badminton-court-2.jpg");
      v6.setTimeSlots(Arrays.asList("07.00-08.00", "08.00-09.00", "10.00-11.00", "13.00-14.00", "14.00-15.00"));
      venues.add(v6);

      return venues;
   }

   public static List<Court> getCourtsForVenue(String venueId) {
      List<Court> courts = new ArrayList<>();

      if ("longfield-sport-center".equals(venueId)) {
         courts.add(createCourt("lapangan-sejahtera", "Lapangan Sejahtera", "indoor-soccer-field.jpg", 24000));
         courts.add(createCourt("lapangan-makmur", "Lapangan Makmur", "indoor-futsal-court.jpg", 25000));
      } else if ("culture-padel".equals(venueId)) {
         courts.add(createCourt("court-1", "Court Premium 1", "padel-court-1.jpg", 40000));
         courts.add(createCourt("court-2", "Court Premium 2", "padel-court-2.jpg", 40000));
      } else if ("balistic-badminton".equals(venueId)) {
         courts.add(createCourt("court-a", "Court A", "badminton-court-a.jpg", 35000));
         courts.add(createCourt("court-b", "Court B", "badminton-court-b.jpg", 35000));
      } else if ("sumber-sari-jaya".equals(venueId)) {
         courts.add(createCourt("lapangan-1", "Lapangan Mini Soccer 1", "mini-soccer-1.jpg", 25000));
         courts.add(createCourt("lapangan-2", "Lapangan Mini Soccer 2", "mini-soccer-2.jpg", 25000));
      } else if ("singhasari-tennis-club".equals(venueId)) {
         courts.add(createCourt("tennis-1", "Tennis Court 1", "tennis-court-1.jpg", 40000));
         courts.add(createCourt("tennis-2", "Tennis Court 2", "tennis-court-2.jpg", 40000));
      } else if ("balistic-badminton-2".equals(venueId)) {
         courts.add(createCourt("court-c", "Court C", "badminton-court-c.jpg", 35000));
         courts.add(createCourt("court-d", "Court D", "badminton-court-d.jpg", 35000));
      }

      return courts;
   }

   private static Court createCourt(String id, String name, String image, double basePrice) {
      Court court = new Court(id, name,
            "Fasilitas berkualitas dengan penerangan malam dan area bersih. Cocok untuk latihan, pertandingan persahabatan, serta kegiatan komunitas.",
            image);

      court.addTimeSlot(new TimeSlot("06.00-07.00", basePrice, true));
      court.addTimeSlot(new TimeSlot("07.00-08.00", basePrice, true));
      court.addTimeSlot(new TimeSlot("09.00-10.00", basePrice, true));
      court.addTimeSlot(new TimeSlot("10.00-11.00", basePrice, false));
      court.addTimeSlot(new TimeSlot("11.00-12.00", basePrice, false));
      court.addTimeSlot(new TimeSlot("13.00-14.00", basePrice, false));
      court.addTimeSlot(new TimeSlot("14.00-15.00", basePrice, true));
      court.addTimeSlot(new TimeSlot("16.00-17.00", basePrice, true));

      return court;
   }
}
