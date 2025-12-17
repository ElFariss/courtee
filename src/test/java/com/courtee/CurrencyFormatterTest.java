package com.courtee;

import com.courtee.utils.CurrencyFormatter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CurrencyFormatterTest {
    
    @Test
    public void testFormatCurrency() {
        String formatted = CurrencyFormatter.format(25000);
        
        assertNotNull(formatted);
        assertTrue(formatted.contains("Rp"));
    }
    
    @Test
    public void testFormatWithoutSymbol() {
        String formatted = CurrencyFormatter.formatWithoutSymbol(25000);
        
        assertNotNull(formatted);
        assertTrue(formatted.contains("25"));
        assertTrue(formatted.contains(".00"));
    }
    
    @Test
    public void testFormatZero() {
        String formatted = CurrencyFormatter.format(0);
        
        assertNotNull(formatted);
        assertTrue(formatted.contains("0"));
    }
    
    @Test
    public void testFormatLargeAmount() {
        String formatted = CurrencyFormatter.format(1000000);
        
        assertNotNull(formatted);
        assertTrue(formatted.contains("1"));
    }
    
    @Test
    public void testFormatDecimal() {
        String formatted = CurrencyFormatter.format(25500.50);
        
        assertNotNull(formatted);
        assertTrue(formatted.contains("25"));
    }
}
