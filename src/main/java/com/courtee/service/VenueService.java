package com.courtee.service;

import com.courtee.model.Court;
import com.courtee.model.Venue;
import com.courtee.utils.DataRepository;

import java.util.List;

public class VenueService implements IVenueService {
    
    @Override
    public List<Venue> getAllVenues() {
        return DataRepository.getVenues();
    }
    
    @Override
    public Venue getVenueById(String venueId) {
        return getAllVenues().stream()
            .filter(v -> v.getId().equals(venueId))
            .findFirst()
            .orElse(null);
    }
    
    @Override
    public List<Court> getCourtsForVenue(String venueId) {
        return DataRepository.getCourtsForVenue(venueId);
    }
}
