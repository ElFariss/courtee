package com.courtee.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.courtee.controller.NavigationController;
import com.courtee.model.Court;
import com.courtee.model.TimeSlot;
import com.courtee.utils.DataRepository;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class VenueDetailView extends BorderPane {

   private NavigationController navigationController;
   private String venueId;
   private Map<String, List<TimeSlot>> selectedSlots = new HashMap<>();
   private Button checkoutButton;

   public VenueDetailView(NavigationController navigationController, String venueId) {
      this.navigationController = navigationController;
      this.venueId = venueId;
      initUI();
   }

   private void initUI() {
      VBox header = createHeader();
      VBox content = createContent();

      ScrollPane scrollPane = new ScrollPane(content);
      scrollPane.setFitToWidth(true);
      scrollPane.setStyle("-fx-background-color: white;");

      setTop(header);
      setCenter(scrollPane);
   }

   private VBox createHeader() {
      VBox header = new VBox();
      header.setPadding(new Insets(15));
      header.setStyle("-fx-background-color: white; -fx-border-color: #EAECF0; -fx-border-width: 0 0 1 0;");

      HBox headerContent = new HBox(20);
      headerContent.setAlignment(Pos.CENTER_LEFT);

      Button backButton = new Button("← Back");
      backButton.setStyle(
            "-fx-background-color: #9957B3; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 8 15;");
      backButton.setOnAction(e -> navigationController.showHome());

      Label title = new Label("Venue Details");
      title.setFont(Font.font("System", FontWeight.BOLD, 20));

      headerContent.getChildren().addAll(backButton, title);
      header.getChildren().add(headerContent);

      return header;
   }

   private VBox createContent() {
      VBox content = new VBox(20);
      content.setPadding(new Insets(30));

      Label venueName = new Label("Longfield Sport Center");
      venueName.setFont(Font.font("System", FontWeight.BOLD, 32));

      Label location = new Label("Jakarta Pusat");
      location.setTextFill(Color.web("#6B7280"));
      location.setFont(Font.font("System", 14));

      ComboBox<String> dateCombo = new ComboBox<>();
      dateCombo.getItems().addAll("Tentukan Tanggal", "20 Desember 2025", "21 Desember 2025", "22 Desember 2025");
      dateCombo.setValue("20 Desember 2025");
      dateCombo.setMaxWidth(250);
      dateCombo.setStyle("-fx-padding: 10; -fx-background-radius: 8;");

      Label courtsTitle = new Label("Lapangan yang Tersedia");
      courtsTitle.setFont(Font.font("System", FontWeight.BOLD, 20));

      VBox courtsSection = new VBox(15);
      List<Court> courts = DataRepository.getCourtsForVenue(venueId);

      for (Court court : courts) {
         VBox courtCard = createCourtCard(court);
         courtsSection.getChildren().add(courtCard);
      }

      checkoutButton = new Button("Lanjutkan ke Pembayaran");
      checkoutButton.setMaxWidth(Double.MAX_VALUE);
      checkoutButton.setStyle(
            "-fx-background-color: #9957B3; -fx-text-fill: white; -fx-font-size: 16; -fx-font-weight: bold; -fx-padding: 15; -fx-cursor: hand; -fx-background-radius: 8;");
      checkoutButton.setDisable(true);
      checkoutButton.setOnAction(e -> {
         if (hasSelectedSlots()) {
            navigationController.showCheckout(getSelectedBookings());
         }
      });

      content.getChildren().addAll(venueName, location, dateCombo, courtsTitle, courtsSection, checkoutButton);

      return content;
   }

   private VBox createCourtCard(Court court) {
      VBox card = new VBox(10);
      card.setPadding(new Insets(15));
      card.setStyle(
            "-fx-background-color: #f9fafb; -fx-border-color: #EAECF0; -fx-border-radius: 8; -fx-background-radius: 8;");

      VBox details = new VBox(10);
      details.setPadding(new Insets(10));

      Label courtName = new Label(court.getName());
      courtName.setFont(Font.font("System", FontWeight.BOLD, 18));
      courtName.setTextFill(Color.web("#9957B3"));

      Label description = new Label(court.getDescription());
      description.setWrapText(true);
      description.setMaxWidth(400);
      description.setTextFill(Color.web("#6B7280"));
      description.setFont(Font.font("System", 12));

      FlowPane timeSlots = new FlowPane();
      timeSlots.setHgap(8);
      timeSlots.setVgap(8);

      selectedSlots.put(court.getId(), new ArrayList<>());

      for (TimeSlot slot : court.getTimeSlots()) {
         HBox timeSlotBox = createTimeSlotButton(court.getId(), slot);
         timeSlots.getChildren().add(timeSlotBox);
      }

      details.getChildren().addAll(courtName, description, timeSlots);

      card.getChildren().add(details);

      return card;
   }

   private HBox createTimeSlotButton(String courtId, TimeSlot slot) {
      HBox slotBox = new HBox();
      slotBox.setStyle("-fx-border-color: #EAECF0; -fx-border-radius: 8; -fx-background-radius: 8;");

      Label timeLabel = new Label(slot.getTime());
      timeLabel.setStyle("-fx-padding: 8 12; -fx-background-color: " + (slot.isAvailable() ? "#9957B3" : "#D1D5DB")
            + "; -fx-text-fill: white; -fx-background-radius: 8 0 0 8;");
      timeLabel.setFont(Font.font("System", FontWeight.BOLD, 11));

      Label priceLabel = new Label(slot.getFormattedPrice());
      priceLabel.setStyle("-fx-padding: 8 12; -fx-background-color: " + (slot.isAvailable() ? "#9957B3" : "#D1D5DB")
            + "; -fx-text-fill: white; -fx-background-radius: 0 8 8 0;");
      priceLabel.setFont(Font.font("System", 11));

      slotBox.getChildren().addAll(timeLabel, priceLabel);

      if (slot.isAvailable()) {
         slotBox.setCursor(javafx.scene.Cursor.HAND);
         slotBox.setOnMouseClicked(e -> {
            List<TimeSlot> courtSlots = selectedSlots.get(courtId);
            boolean isSelected = courtSlots.contains(slot);

            if (isSelected) {
               courtSlots.remove(slot);
               timeLabel.setStyle(
                     "-fx-padding: 8 12; -fx-background-color: #9957B3; -fx-text-fill: white; -fx-background-radius: 8 0 0 8;");
               priceLabel.setStyle(
                     "-fx-padding: 8 12; -fx-background-color: #9957B3; -fx-text-fill: white; -fx-background-radius: 0 8 8 0;");
            } else {
               courtSlots.add(slot);
               timeLabel.setStyle(
                     "-fx-padding: 8 12; -fx-background-color: #008733; -fx-text-fill: white; -fx-background-radius: 8 0 0 8;");
               priceLabel.setStyle(
                     "-fx-padding: 8 12; -fx-background-color: #008733; -fx-text-fill: white; -fx-background-radius: 0 8 8 0;");
            }

            updateCheckoutButton();
         });
      } else {
         slotBox.setOpacity(0.5);
      }

      return slotBox;
   }

   private boolean hasSelectedSlots() {
      for (List<TimeSlot> slots : selectedSlots.values()) {
         if (!slots.isEmpty()) {
            return true;
         }
      }
      return false;
   }

   private void updateCheckoutButton() {
      checkoutButton.setDisable(!hasSelectedSlots());
   }

   private List<com.courtee.model.Booking> getSelectedBookings() {
      List<com.courtee.model.Booking> bookings = new ArrayList<>();

      for (Map.Entry<String, List<TimeSlot>> entry : selectedSlots.entrySet()) {
         String courtId = entry.getKey();
         for (TimeSlot slot : entry.getValue()) {
            bookings.add(new com.courtee.model.Booking(
                  "Longfield Sport Center",
                  courtId,
                  "20 Desember 2025",
                  slot.getTime(),
                  slot.getPrice()));
         }
      }

      return bookings;
   }
}
