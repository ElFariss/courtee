package com.courtee.service;

import com.courtee.model.Venue;
import com.courtee.utils.DataRepository;

import java.util.List;
import java.util.stream.Collectors;

public class SearchService implements ISearchService {
    
    @Override
    public List<Venue> searchVenues(String area, String sportType) {
        List<Venue> allVenues = DataRepository.getVenues();
        List<Venue> filtered = allVenues;
        
        if (area != null && !area.trim().isEmpty()) {
            filtered = filterByArea(filtered, area);
        }
        
        if (sportType != null && !sportType.equals("Semua Jenis Olahraga")) {
            filtered = filterBySportType(filtered, sportType);
        }
        
        return filtered;
    }
    
    @Override
    public List<Venue> filterByArea(List<Venue> venues, String area) {
        String searchArea = area.toLowerCase().trim();
        return venues.stream()
            .filter(venue -> venue.getLocation().toLowerCase().contains(searchArea))
            .collect(Collectors.toList());
    }
    
    @Override
    public List<Venue> filterBySportType(List<Venue> venues, String sportType) {
        return venues.stream()
            .filter(venue -> venue.getType().equalsIgnoreCase(sportType))
            .collect(Collectors.toList());
    }
}
