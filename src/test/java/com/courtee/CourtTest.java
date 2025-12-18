package com.courtee;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.courtee.model.Court;
import com.courtee.model.TimeSlot;

public class CourtTest {

   private Court court;

   @BeforeEach
   public void setUp() {
      court = new Court("court-1", "Main Court",
            "Premium synthetic grass field", "court-image.jpg");
   }

   @Test
   public void testCourtCreation() {
      assertNotNull(court);
      assertEquals("court-1", court.getId());
      assertEquals("Main Court", court.getName());
      assertEquals("Premium synthetic grass field", court.getDescription());
      assertEquals("court-image.jpg", court.getImage());
   }

   @Test
   public void testAddTimeSlot() {
      TimeSlot slot1 = new TimeSlot(1, "08.00-09.00", 25000, true, "court-1");
      TimeSlot slot2 = new TimeSlot(2, "09.00-10.00", 25000, true, "court-1");

      court.addTimeSlot(slot1);
      court.addTimeSlot(slot2);

      assertEquals(2, court.getTimeSlots().size());
      assertEquals(slot1, court.getTimeSlots().get(0));
      assertEquals(slot2, court.getTimeSlots().get(1));
   }

   @Test
   public void testSetTimeSlots() {
      TimeSlot slot1 = new TimeSlot(1, "10.00-11.00", 30000, true, "court-1");
      TimeSlot slot2 = new TimeSlot(2, "11.00-12.00", 30000, false, "court-1");

      court.setTimeSlots(Arrays.asList(slot1, slot2));

      assertEquals(2, court.getTimeSlots().size());
      assertTrue(court.getTimeSlots().get(0).isAvailable());
      assertFalse(court.getTimeSlots().get(1).isAvailable());
   }

   @Test
   public void testCourtSetters() {
      court.setName("Updated Court");
      court.setDescription("New description");
      court.setImage("new-image.jpg");

      assertEquals("Updated Court", court.getName());
      assertEquals("New description", court.getDescription());
      assertEquals("new-image.jpg", court.getImage());
   }

   @Test
   public void testEmptyTimeSlots() {
      assertTrue(court.getTimeSlots().isEmpty());
   }
}
