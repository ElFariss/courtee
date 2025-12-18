package com.courtee;

import com.courtee.controller.NavigationController;
import com.courtee.database.DatabaseInitializer;
import javafx.application.Application;
import javafx.stage.Stage;

public class CourteeApp extends Application {

   @Override
   public void start(Stage primaryStage) {
      // Initialize database with sample data
      DatabaseInitializer.initializeData();

      NavigationController navigationController = new NavigationController(primaryStage);

      primaryStage.setTitle("Courtee - Sports Venue Booking");
      primaryStage.setMinWidth(1000);
      primaryStage.setMinHeight(700);

      navigationController.showHome();

      primaryStage.show();
   }

   public static void main(String[] args) {
      launch(args);
   }
}
