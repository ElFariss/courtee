package com.courtee.utils;

import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyFormatter {
    
    private static final NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
    
    public static String format(double amount) {
        return formatter.format(amount).replace("IDR", "Rp");
    }
    
    public static String formatWithoutSymbol(double amount) {
        return String.format("%,.2f", amount);
    }
}
