package com.courtee.model;

import java.util.ArrayList;
import java.util.List;

public class Venue {
   private String id;
   private String name;
   private String type;
   private String location;
   private double pricePerHour;
   private String image; // Image filename for reference
   private byte[] imageData; // Binary image data from database
   private List<String> timeSlots;

   public Venue(String id, String name, String type, String location, double pricePerHour, String image) {
      this.id = id;
      this.name = name;
      this.type = type;
      this.location = location;
      this.pricePerHour = pricePerHour;
      this.image = image;
      this.timeSlots = new ArrayList<>();
   }

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getType() {
      return type;
   }

   public void setType(String type) {
      this.type = type;
   }

   public String getLocation() {
      return location;
   }

   public void setLocation(String location) {
      this.location = location;
   }

   public double getPricePerHour() {
      return pricePerHour;
   }

   public void setPricePerHour(double pricePerHour) {
      this.pricePerHour = pricePerHour;
   }

   public String getImage() {
      return image;
   }

   public void setImage(String image) {
      this.image = image;
   }

   public List<String> getTimeSlots() {
      return timeSlots;
   }

   public void setTimeSlots(List<String> timeSlots) {
      this.timeSlots = timeSlots;
   }

   public void addTimeSlot(String timeSlot) {
      this.timeSlots.add(timeSlot);
   }

   public byte[] getImageData() {
      return imageData;
   }

   public void setImageData(byte[] imageData) {
      this.imageData = imageData;
   }

   public String getFormattedPrice() {
      return String.format("Rp %.3f/jam", pricePerHour);
   }
}
