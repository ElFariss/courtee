package com.courtee.service;

import com.courtee.model.Booking;

public interface IPaymentService {
    boolean processPayment(Booking booking, String method);
    boolean processRefund(Booking booking);
    String generateVirtualAccount();
    String generateQRCode();
}
