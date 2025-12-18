package com.courtee.service;

import com.courtee.database.dao.CourtDAO;
import com.courtee.database.dao.VenueDAO;
import com.courtee.model.Court;
import com.courtee.model.Venue;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VenueService implements IVenueService {

   private final VenueDAO venueDAO;
   private final CourtDAO courtDAO;

   public VenueService() {
      this.venueDAO = new VenueDAO();
      this.courtDAO = new CourtDAO();
   }

   @Override
   public List<Venue> getAllVenues() {
      try {
         return venueDAO.getAllVenues();
      } catch (SQLException e) {
         System.err.println("Error fetching venues: " + e.getMessage());
         return new ArrayList<>();
      }
   }

   @Override
   public Venue getVenueById(String venueId) {
      try {
         return venueDAO.getVenueById(venueId);
      } catch (SQLException e) {
         System.err.println("Error fetching venue: " + e.getMessage());
         return null;
      }
   }

   @Override
   public List<Court> getCourtsForVenue(String venueId) {
      try {
         return courtDAO.getCourtsByVenue(venueId);
      } catch (SQLException e) {
         System.err.println("Error fetching courts: " + e.getMessage());
         return new ArrayList<>();
      }
   }
}
