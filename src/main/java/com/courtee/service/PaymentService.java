package com.courtee.service;

import com.courtee.model.Booking;

public class PaymentService implements IPaymentService {
    
    @Override
    public boolean processPayment(Booking booking, String method) {
        // Simulate payment processing
        System.out.println("Processing payment for booking: " + booking.getVenueName());
        System.out.println("Payment method: " + method);
        return true;
    }
    
    @Override
    public boolean processRefund(Booking booking) {
        System.out.println("Processing refund for booking: " + booking.getVenueName());
        return true;
    }
    
    @Override
    public String generateVirtualAccount() {
        return "1204-34248-3235";
    }
    
    @Override
    public String generateQRCode() {
        return "QR_CODE_DATA_HERE";
    }
}
