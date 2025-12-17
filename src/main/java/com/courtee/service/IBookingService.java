package com.courtee.service;

import com.courtee.model.Booking;
import com.courtee.model.TimeSlot;

import java.util.List;

public interface IBookingService {
    Booking createBooking(String venueName, String courtName, String date, TimeSlot timeSlot);
    boolean cancelBooking(Booking booking);
    List<Booking> getUserBookings(String userId);
}
