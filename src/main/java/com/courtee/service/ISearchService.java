package com.courtee.service;

import com.courtee.model.Venue;
import java.util.List;

public interface ISearchService {
    List<Venue> searchVenues(String area, String sportType);
    List<Venue> filterByArea(List<Venue> venues, String area);
    List<Venue> filterBySportType(List<Venue> venues, String sportType);
}
