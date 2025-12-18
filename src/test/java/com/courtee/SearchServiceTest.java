package com.courtee;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.courtee.database.DatabaseInitializer;
import com.courtee.model.Venue;
import com.courtee.service.SearchService;

public class SearchServiceTest {

   private SearchService searchService;

   @BeforeAll
   public static void initDatabase() {
      // Initialize database with sample data once for all tests
      DatabaseInitializer.initializeData();
   }

   @BeforeEach
   public void setUp() {
      searchService = new SearchService();
   }

   @Test
   public void testSearchByArea() {
      List<Venue> results = searchService.searchVenues("Jakarta Barat", "Semua Jenis Olahraga");

      assertNotNull(results);
      assertFalse(results.isEmpty());
      assertTrue(results.stream().allMatch(v -> v.getLocation().contains("Jakarta Barat")));
   }

   @Test
   public void testSearchBySportType() {
      List<Venue> results = searchService.searchVenues("", "Lapangan Badminton");

      assertNotNull(results);
      assertEquals(2, results.size()); // Should find 2 badminton venues
      assertTrue(results.stream().allMatch(v -> v.getType().equals("Lapangan Badminton")));
   }

   @Test
   public void testSearchByAreaAndSportType() {
      List<Venue> results = searchService.searchVenues("Jawa", "Lapangan Badminton");

      assertNotNull(results);
      assertEquals(2, results.size()); // 2 badminton venues in Jawa (Timur and Tengah)
      assertTrue(results.stream().allMatch(v -> v.getLocation().contains("Jawa") &&
            v.getType().equals("Lapangan Badminton")));
   }

   @Test
   public void testSearchNoResults() {
      List<Venue> results = searchService.searchVenues("Bandung", "Semua Jenis Olahraga");

      assertNotNull(results);
      assertTrue(results.isEmpty());
   }

   @Test
   public void testFilterByAreaCaseInsensitive() {
      List<Venue> results = searchService.searchVenues("jakarta barat", "Semua Jenis Olahraga");

      assertNotNull(results);
      assertFalse(results.isEmpty());
   }

   @Test
   public void testFilterByAreaPartialMatch() {
      List<Venue> results = searchService.searchVenues("Jakarta", "Semua Jenis Olahraga");

      assertNotNull(results);
      assertEquals(2, results.size()); // 2 venues in Jakarta (Barat and Timur)
   }

   @Test
   public void testFilterBySportTypeExact() {
      List<Venue> results = searchService.searchVenues("", "Lapangan Sepak Bola");

      assertNotNull(results);
      assertEquals(1, results.size());
      assertEquals("Lapangan Sepak Bola", results.get(0).getType());
   }
}
