package com.courtee.controller;

import com.courtee.model.Booking;
import com.courtee.view.*;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;

public class NavigationController {
    
    private Stage primaryStage;
    private Scene scene;
    
    public NavigationController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    
    public void showHome() {
        HomeView homeView = new HomeView(this);
        scene = new Scene(homeView, 1200, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Courtee - Home");
    }
    
    public void showVenueDetail(String venueId) {
        VenueDetailView venueDetailView = new VenueDetailView(this, venueId);
        scene = new Scene(venueDetailView, 1200, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Courtee - Venue Details");
    }
    
    public void showCheckout(List<Booking> bookings) {
        CheckoutView checkoutView = new CheckoutView(this, bookings);
        scene = new Scene(checkoutView, 1200, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Courtee - Checkout");
    }
    
    public void showQRISPayment(List<Booking> bookings) {
        QRISPaymentView qrisView = new QRISPaymentView(this, bookings);
        scene = new Scene(qrisView, 1200, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Courtee - QRIS Payment");
    }
    
    public void showMobileBankingPayment(List<Booking> bookings) {
        MobileBankingPaymentView mbView = new MobileBankingPaymentView(this, bookings);
        scene = new Scene(mbView, 1200, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Courtee - Mobile Banking Payment");
    }
}
