package com.courtee;

import com.courtee.model.Booking;
import com.courtee.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentServiceTest {
    
    private PaymentService paymentService;
    
    @BeforeEach
    public void setUp() {
        paymentService = new PaymentService();
    }
    
    @Test
    public void testProcessPayment() {
        Booking booking = new Booking("Test Venue", "Test Court", "20 Dec 2025", "09.00-10.00", 25000);
        
        boolean result = paymentService.processPayment(booking, "qris");
        
        assertTrue(result);
    }
    
    @Test
    public void testProcessRefund() {
        Booking booking = new Booking("Test Venue", "Test Court", "20 Dec 2025", "09.00-10.00", 25000);
        
        boolean result = paymentService.processRefund(booking);
        
        assertTrue(result);
    }
    
    @Test
    public void testGenerateVirtualAccount() {
        String va = paymentService.generateVirtualAccount();
        
        assertNotNull(va);
        assertEquals("1204-34248-3235", va);
    }
    
    @Test
    public void testGenerateQRCode() {
        String qr = paymentService.generateQRCode();
        
        assertNotNull(qr);
        assertFalse(qr.isEmpty());
    }
}
