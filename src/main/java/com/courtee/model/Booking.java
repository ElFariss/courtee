package com.courtee.model;

public class Booking {
    private String venueName;
    private String courtName;
    private String date;
    private String time;
    private double price;

    public Booking(String venueName, String courtName, String date, String time, double price) {
        this.venueName = venueName;
        this.courtName = courtName;
        this.date = date;
        this.time = time;
        this.price = price;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getCourtName() {
        return courtName;
    }

    public void setCourtName(String courtName) {
        this.courtName = courtName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getFormattedPrice() {
        return String.format("Rp %.2f", price);
    }
}
