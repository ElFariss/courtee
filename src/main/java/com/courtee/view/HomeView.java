package com.courtee.view;

import java.util.List;

import com.courtee.controller.NavigationController;
import com.courtee.model.Venue;
import com.courtee.service.VenueService;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class HomeView extends BorderPane {

   private NavigationController navigationController;
   private List<Venue> allVenues;
   private GridPane venueGrid;
   private TextField areaField;
   private ComboBox<String> sportTypeCombo;
   private VenueService venueService;

   public HomeView(NavigationController navigationController) {
      this.navigationController = navigationController;
      this.venueService = new VenueService();
      this.allVenues = venueService.getAllVenues();
      initUI();
   }

   private void initUI() {
      VBox topSection = createHeader();
      VBox searchSection = createSearchHero();
      VBox venueSection = createVenueSection();
      VBox footerSection = createFooter();

      VBox mainContent = new VBox(searchSection, venueSection);
      mainContent.setSpacing(20);

      ScrollPane scrollPane = new ScrollPane(mainContent);
      scrollPane.setFitToWidth(true);
      scrollPane.setStyle("-fx-background-color: white;");

      setTop(topSection);
      setCenter(scrollPane);
      setBottom(footerSection);
   }

   private VBox createHeader() {
      VBox header = new VBox();
      header.setPadding(new Insets(15));
      header.setStyle("-fx-background-color: white; -fx-border-color: #EAECF0; -fx-border-width: 0 0 1 0;");

      HBox headerContent = new HBox(20);
      headerContent.setAlignment(Pos.CENTER_LEFT);

      Label logo = new Label("C");
      logo.setFont(Font.font("System", FontWeight.BOLD, 16));
      logo.setTextFill(Color.WHITE);
      logo.setStyle(
            "-fx-background-color: #9957B3; -fx-background-radius: 50%; -fx-min-width: 32; -fx-min-height: 32; -fx-alignment: center;");

      Label brandName = new Label("ourtee");
      brandName.setFont(Font.font("System", FontWeight.BOLD, 20));

      HBox logoBox = new HBox(5, logo, brandName);
      logoBox.setAlignment(Pos.CENTER_LEFT);

      HBox nav = new HBox(20);
      nav.setAlignment(Pos.CENTER_LEFT);
      Button homeBtn = new Button("Home");
      homeBtn.setStyle(
            "-fx-background-color: transparent; -fx-text-fill: #9957B3; -fx-font-weight: bold; -fx-cursor: hand;");
      Button venueBtn = new Button("Venue");
      venueBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #6B7280; -fx-cursor: hand;");
      Button communityBtn = new Button("Community");
      communityBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #6B7280; -fx-cursor: hand;");
      nav.getChildren().addAll(homeBtn, venueBtn, communityBtn);

      Region spacer = new Region();
      HBox.setHgrow(spacer, Priority.ALWAYS);

      Label userName = new Label("Ahmad Brandon");
      userName.setFont(Font.font("System", FontWeight.NORMAL, 14));

      headerContent.getChildren().addAll(logoBox, nav, spacer, userName);
      header.getChildren().add(headerContent);

      return header;
   }

   private VBox createSearchHero() {
      VBox searchHero = new VBox(15);
      searchHero.setPadding(new Insets(40));
      searchHero.setAlignment(Pos.CENTER);
      searchHero.setStyle(
            "-fx-background-color: linear-gradient(to right, rgba(153, 87, 179, 0.3), rgba(153, 87, 179, 0.1));");
      searchHero.setMinHeight(250);

      Label searchTitle = new Label("Cari Venue Olahraga");
      searchTitle.setFont(Font.font("System", FontWeight.BOLD, 28));
      searchTitle.setTextFill(Color.web("#9957B3"));

      areaField = new TextField();
      areaField.setPromptText("Daerah (e.g., Jakarta Barat, Jakarta Pusat)");
      areaField.setMaxWidth(500);
      areaField.setStyle("-fx-padding: 10; -fx-background-radius: 8; -fx-border-color: #EAECF0; -fx-border-radius: 8;");
      areaField.textProperty().addListener((obs, oldVal, newVal) -> filterVenues());

      sportTypeCombo = new ComboBox<>();
      sportTypeCombo.getItems().addAll("Semua Jenis Olahraga", "Lapangan Sepak Bola", "Lapangan Badminton",
            "Lapangan Tennis", "Lapangan Padel", "Lapangan Mini Soccer");
      sportTypeCombo.setValue("Semua Jenis Olahraga");
      sportTypeCombo.setMaxWidth(500);
      sportTypeCombo.setStyle("-fx-padding: 10; -fx-background-radius: 8;");
      sportTypeCombo.setOnAction(e -> filterVenues());

      ComboBox<String> dateCombo = new ComboBox<>();
      dateCombo.getItems().addAll("Tanggal", "Hari Ini", "Besok", "Pilih Tanggal");
      dateCombo.setValue("Tanggal");
      dateCombo.setMaxWidth(500);
      dateCombo.setStyle("-fx-padding: 10; -fx-background-radius: 8;");

      Button searchButton = new Button("Cari Venue");
      searchButton.setMaxWidth(500);
      searchButton.setStyle("-fx-background-color: #9957B3; -fx-text-fill: white; -fx-font-weight: bold; " +
            "-fx-padding: 12; -fx-background-radius: 8; -fx-cursor: hand; -fx-font-size: 14;");
      searchButton.setOnAction(e -> filterVenues());

      Button resetButton = new Button("Reset Filter");
      resetButton.setMaxWidth(500);
      resetButton.setStyle("-fx-background-color: #6B7280; -fx-text-fill: white; -fx-font-weight: bold; " +
            "-fx-padding: 10; -fx-background-radius: 8; -fx-cursor: hand; -fx-font-size: 12;");
      resetButton.setOnAction(e -> resetFilters());

      searchHero.getChildren().addAll(searchTitle, areaField, sportTypeCombo, dateCombo, searchButton, resetButton);

      return searchHero;
   }

   private VBox createVenueSection() {
      VBox venueSection = new VBox(15);
      venueSection.setPadding(new Insets(20, 40, 20, 40));

      Label title = new Label("Rekomendasi Venue");
      title.setFont(Font.font("System", FontWeight.BOLD, 24));
      title.setTextFill(Color.web("#9957B3"));

      venueGrid = new GridPane();
      venueGrid.setHgap(15);
      venueGrid.setVgap(15);

      displayVenues(allVenues);

      venueSection.getChildren().addAll(title, venueGrid);
      return venueSection;
   }

   private void displayVenues(List<Venue> venues) {
      venueGrid.getChildren().clear();

      if (venues.isEmpty()) {
         Label noResults = new Label("Tidak ada venue yang sesuai dengan pencarian Anda.");
         noResults.setFont(Font.font("System", FontWeight.NORMAL, 16));
         noResults.setTextFill(Color.web("#6B7280"));
         venueGrid.add(noResults, 0, 0);
         return;
      }

      int col = 0;
      int row = 0;

      for (Venue venue : venues) {
         VBox venueCard = createVenueCard(venue);
         venueGrid.add(venueCard, col, row);

         col++;
         if (col == 3) {
            col = 0;
            row++;
         }
      }
   }

   private void filterVenues() {
      String area = areaField.getText().toLowerCase().trim();
      String sportType = sportTypeCombo.getValue();

      List<Venue> filtered = allVenues.stream()
            .filter(venue -> {
               boolean matchesArea = area.isEmpty() ||
                     venue.getLocation().toLowerCase().contains(area);

               boolean matchesSport = sportType.equals("Semua Jenis Olahraga") ||
                     venue.getType().equalsIgnoreCase(sportType);

               return matchesArea && matchesSport;
            })
            .collect(java.util.stream.Collectors.toList());

      displayVenues(filtered);
   }

   private void resetFilters() {
      areaField.clear();
      sportTypeCombo.setValue("Semua Jenis Olahraga");
      displayVenues(allVenues);
   }

   private VBox createVenueCard(Venue venue) {
      VBox card = new VBox(10);
      card.setPadding(new Insets(15));
      card.setStyle(
            "-fx-background-color: white; -fx-border-color: #EAECF0; -fx-border-radius: 8; -fx-background-radius: 8; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2);");
      card.setPrefWidth(250);
      card.setCursor(javafx.scene.Cursor.HAND);

      // Load and display the image
      ImageView imageView;
      try {
         Image image;
         // Try to load from BLOB data first, fallback to resource files
         if (venue.getImageData() != null && venue.getImageData().length > 0) {
            image = new Image(new java.io.ByteArrayInputStream(venue.getImageData()));
         } else {
            image = new Image(getClass().getResourceAsStream("/" + venue.getImage()));
         }
         imageView = new ImageView(image);
         imageView.setFitWidth(250);
         imageView.setFitHeight(150);
         imageView.setPreserveRatio(false);
         imageView.setStyle("-fx-background-radius: 8;");
      } catch (Exception e) {
         // Fallback to placeholder if image not found
         Label imagePlaceholder = new Label("[" + venue.getImage() + "]");
         imagePlaceholder.setPrefHeight(150);
         imagePlaceholder.setAlignment(Pos.CENTER);
         imagePlaceholder.setMaxWidth(Double.MAX_VALUE);
         imagePlaceholder.setStyle("-fx-background-color: #E5E7EB; -fx-background-radius: 8;");
         imageView = null;
      }

      Label name = new Label(venue.getName());
      name.setFont(Font.font("System", FontWeight.BOLD, 16));

      Label type = new Label(venue.getType());
      type.setTextFill(Color.web("#6B7280"));
      type.setFont(Font.font("System", 12));

      Label location = new Label(venue.getLocation());
      location.setTextFill(Color.web("#6B7280"));
      location.setFont(Font.font("System", 12));

      Label price = new Label(venue.getFormattedPrice());
      price.setTextFill(Color.web("#9957B3"));
      price.setFont(Font.font("System", FontWeight.BOLD, 14));

      FlowPane timeSlots = new FlowPane();
      timeSlots.setHgap(5);
      timeSlots.setVgap(5);

      for (String slot : venue.getTimeSlots()) {
         Label slotLabel = new Label(slot);
         slotLabel.setStyle(
               "-fx-background-color: white; -fx-border-color: #EAECF0; -fx-border-radius: 5; -fx-background-radius: 5; -fx-padding: 5 10;");
         slotLabel.setFont(Font.font("System", 10));
         slotLabel.setTextFill(Color.web("#6B7280"));
         timeSlots.getChildren().add(slotLabel);
      }

      if (imageView != null) {
         card.getChildren().addAll(imageView, name, type, location, price, timeSlots);
      } else {
         // Use placeholder if image failed to load
         Label imagePlaceholder = new Label("[" + venue.getImage() + "]");
         imagePlaceholder.setPrefHeight(150);
         imagePlaceholder.setAlignment(Pos.CENTER);
         imagePlaceholder.setMaxWidth(Double.MAX_VALUE);
         imagePlaceholder.setStyle("-fx-background-color: #E5E7EB; -fx-background-radius: 8;");
         card.getChildren().addAll(imagePlaceholder, name, type, location, price, timeSlots);
      }

      card.setOnMouseClicked(e -> navigationController.showVenueDetail(venue.getId()));

      return card;
   }

   private VBox createFooter() {
      VBox footer = new VBox(15);
      footer.setPadding(new Insets(30));
      footer.setAlignment(Pos.CENTER);
      footer.setStyle("-fx-background-color: white;");

      HBox links = new HBox(20);
      links.setAlignment(Pos.CENTER);
      String[] linkTexts = { "About", "Blog", "Jobs", "Press", "Accessibility", "Partners" };
      for (String text : linkTexts) {
         Label link = new Label(text);
         link.setTextFill(Color.web("#6B7280"));
         link.setFont(Font.font("System", 12));
         link.setCursor(javafx.scene.Cursor.HAND);
         links.getChildren().add(link);
      }

      Label copyright = new Label("© 2020 Courtee, Inc. All rights reserved.");
      copyright.setTextFill(Color.web("#6B7280"));
      copyright.setFont(Font.font("System", 12));

      footer.getChildren().addAll(links, copyright);

      return footer;
   }
}
