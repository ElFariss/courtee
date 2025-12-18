package com.courtee.model;

public class TimeSlot {
   private int id; // Database ID
   private String time;
   private double price;
   private boolean available;
   private String courtId; // Foreign key to court

   // Constructor for creating new time slots (without ID and courtId)
   public TimeSlot(String time, double price, boolean available) {
      this.time = time;
      this.price = price;
      this.available = available;
   }

   // Full constructor for database retrieval
   public TimeSlot(int id, String time, double price, boolean available, String courtId) {
      this.id = id;
      this.time = time;
      this.price = price;
      this.available = available;
      this.courtId = courtId;
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getCourtId() {
      return courtId;
   }

   public void setCourtId(String courtId) {
      this.courtId = courtId;
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
