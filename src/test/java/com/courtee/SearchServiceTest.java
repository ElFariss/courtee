package com.courtee;

import com.courtee.model.Venue;
import com.courtee.service.SearchService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SearchServiceTest {
    
    private SearchService searchService;
    
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
        List<Venue> results = searchService.searchVenues("Jakarta Barat", "Lapangan Badminton");
        
        assertNotNull(results);
        assertEquals(2, results.size());
        assertTrue(results.stream().allMatch(v -> 
            v.getLocation().contains("Jakarta Barat") && 
            v.getType().equals("Lapangan Badminton")
        ));
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
        assertEquals(6, results.size()); // All venues are in Jakarta
    }
    
    @Test
    public void testFilterBySportTypeExact() {
        List<Venue> results = searchService.searchVenues("", "Lapangan Sepak Bola");
        
        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals("Lapangan Sepak Bola", results.get(0).getType());
    }
}
