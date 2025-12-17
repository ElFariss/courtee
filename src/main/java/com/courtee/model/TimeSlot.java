package com.courtee.model;

public class TimeSlot {
    private String time;
    private double price;
    private boolean available;

    public TimeSlot(String time, double price, boolean available) {
        this.time = time;
        this.price = price;
        this.available = available;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getFormattedPrice() {
        return String.format("Rp %.2f", price);
    }
}
