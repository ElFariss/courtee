package com.courtee.service;

import com.courtee.model.Booking;
import com.courtee.model.TimeSlot;

import java.util.ArrayList;
import java.util.List;

public class BookingService implements IBookingService {
    
    private List<Booking> bookings = new ArrayList<>();
    
    @Override
    public Booking createBooking(String venueName, String courtName, String date, TimeSlot timeSlot) {
        Booking booking = new Booking(venueName, courtName, date, timeSlot.getTime(), timeSlot.getPrice());
        bookings.add(booking);
        return booking;
    }
    
    @Override
    public boolean cancelBooking(Booking booking) {
        return bookings.remove(booking);
    }
    
    @Override
    public List<Booking> getUserBookings(String userId) {
        return new ArrayList<>(bookings);
    }
}
