package com.courtee.model;

import java.util.ArrayList;
import java.util.List;

public class Court {
   private String id;
   private String name;
   private String description;
   private String image; // Image filename for reference
   private byte[] imageData; // Binary image data from database
   private String venueId; // Foreign key to venue
   private List<TimeSlot> timeSlots;

   public Court(String id, String name, String description, String image) {
      this.id = id;
      this.name = name;
      this.description = description;
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

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public String getImage() {
      return image;
   }

   public void setImage(String image) {
      this.image = image;
   }

   public String getVenueId() {
      return venueId;
   }

   public void setVenueId(String venueId) {
      this.venueId = venueId;
   }

   public List<TimeSlot> getTimeSlots() {
      return timeSlots;
   }

   public void setTimeSlots(List<TimeSlot> timeSlots) {
      this.timeSlots = timeSlots;
   }

   public void addTimeSlot(TimeSlot timeSlot) {
      this.timeSlots.add(timeSlot);
   }

   public byte[] getImageData() {
      return imageData;
   }

   public void setImageData(byte[] imageData) {
      this.imageData = imageData;
   }
}
