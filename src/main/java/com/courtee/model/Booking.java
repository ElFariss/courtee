package com.courtee.model;

public class Booking {
   private int id; // Database ID
   private String venueName;
   private String courtName;
   private String courtId; // Court ID for tracking time slot availability
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

   public Booking(String venueName, String courtName, String courtId, String date, String time, double price) {
      this.venueName = venueName;
      this.courtName = courtName;
      this.courtId = courtId;
      this.date = date;
      this.time = time;
      this.price = price;
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
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

   public String getCourtId() {
      return courtId;
   }

   public void setCourtId(String courtId) {
      this.courtId = courtId;
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
