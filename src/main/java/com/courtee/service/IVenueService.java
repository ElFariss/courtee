package com.courtee.service;

import com.courtee.model.Court;
import com.courtee.model.Venue;

import java.util.List;

public interface IVenueService {
    List<Venue> getAllVenues();
    Venue getVenueById(String venueId);
    List<Court> getCourtsForVenue(String venueId);
}
