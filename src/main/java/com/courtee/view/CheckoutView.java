package com.courtee.view;

import com.courtee.controller.NavigationController;
import com.courtee.model.Booking;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.List;

public class CheckoutView extends BorderPane {
    
    private NavigationController navigationController;
    private List<Booking> bookings;
    private String selectedPayment = null; // No default selection
    private VBox qrisBox;
    private VBox mobileBankingBox;
    
    public CheckoutView(NavigationController navigationController, List<Booking> bookings) {
        this.navigationController = navigationController;
        this.bookings = bookings;
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
        backButton.setStyle("-fx-background-color: #9957B3; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 8 15;");
        backButton.setOnAction(e -> navigationController.showVenueDetail("longfield-sport-center"));
        
        Label title = new Label("Detail Pemesanan");
        title.setFont(Font.font("System", FontWeight.BOLD, 20));
        title.setTextFill(Color.web("#9957B3"));
        
        headerContent.getChildren().addAll(backButton, title);
        header.getChildren().add(headerContent);
        
        return header;
    }
    
    private VBox createContent() {
        VBox content = new VBox(20);
        content.setPadding(new Insets(30));
        content.setMaxWidth(700);
        content.setAlignment(Pos.TOP_CENTER);
        
        VBox orderItems = new VBox(10);
        for (Booking booking : bookings) {
            VBox orderCard = createOrderCard(booking);
            orderItems.getChildren().add(orderCard);
        }
        
        VBox summary = createSummary();
        VBox paymentMethods = createPaymentMethods();
        
        Button continueButton = new Button("Lanjutkan ke Konfirmasi Pembayaran");
        continueButton.setMaxWidth(Double.MAX_VALUE);
        continueButton.setStyle("-fx-background-color: #9957B3; -fx-text-fill: white; -fx-font-size: 16; -fx-font-weight: bold; -fx-padding: 15; -fx-cursor: hand; -fx-background-radius: 8;");
        continueButton.setOnAction(e -> showPaymentConfirmation());
        
        content.getChildren().addAll(orderItems, summary, paymentMethods, continueButton);
        
        return content;
    }
    
    private VBox createOrderCard(Booking booking) {
        VBox card = new VBox(5);
        card.setPadding(new Insets(15));
        card.setStyle("-fx-background-color: #f9fafb; -fx-border-color: #EAECF0; -fx-border-radius: 8; -fx-background-radius: 8;");
        
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(header, Priority.ALWAYS);
        
        VBox leftInfo = new VBox(2);
        Label venueName = new Label(booking.getVenueName());
        venueName.setFont(Font.font("System", FontWeight.BOLD, 14));
        venueName.setTextFill(Color.web("#9957B3"));
        
        Label courtName = new Label(booking.getCourtName());
        courtName.setTextFill(Color.web("#6B7280"));
        courtName.setFont(Font.font("System", 12));
        
        leftInfo.getChildren().addAll(venueName, courtName);
        
        HBox rightInfo = new HBox(15);
        rightInfo.setAlignment(Pos.CENTER_RIGHT);
        
        Label date = new Label(booking.getDate());
        date.setTextFill(Color.web("#6B7280"));
        date.setFont(Font.font("System", 12));
        
        Label time = new Label(booking.getTime());
        time.setFont(Font.font("System", FontWeight.BOLD, 12));
        time.setTextFill(Color.web("#9957B3"));
        
        Label price = new Label(booking.getFormattedPrice());
        price.setTextFill(Color.web("#9957B3"));
        price.setFont(Font.font("System", 12));
        
        rightInfo.getChildren().addAll(date, time, price);
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        HBox mainContent = new HBox(10);
        mainContent.getChildren().addAll(leftInfo, spacer, rightInfo);
        
        card.getChildren().add(mainContent);
        
        return card;
    }
    
    private VBox createSummary() {
        VBox summary = new VBox(10);
        summary.setPadding(new Insets(15));
        
        double subtotal = bookings.stream().mapToDouble(Booking::getPrice).sum();
        double tax = subtotal * 0.12;
        double total = subtotal + tax;
        
        HBox subtotalRow = createSummaryRow("Subtotal", String.format("Rp %.2f", subtotal));
        HBox taxRow = createSummaryRow("Pajak (12%)", String.format("Rp %.2f", tax));
        
        Separator separator = new Separator();
        separator.setStyle("-fx-background-color: #9957B3;");
        
        HBox totalRow = createSummaryRow("Total", String.format("Rp %.2f", total));
        totalRow.setStyle("-fx-font-weight: bold;");
        
        summary.getChildren().addAll(subtotalRow, taxRow, separator, totalRow);
        
        return summary;
    }
    
    private HBox createSummaryRow(String label, String value) {
        HBox row = new HBox();
        row.setAlignment(Pos.CENTER);
        
        Label labelText = new Label(label);
        labelText.setTextFill(Color.web("#9957B3"));
        labelText.setFont(Font.font("System", FontWeight.MEDIUM, 14));
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        Label valueText = new Label(value);
        valueText.setTextFill(Color.web("#9957B3"));
        valueText.setFont(Font.font("System", 14));
        
        row.getChildren().addAll(labelText, spacer, valueText);
        
        return row;
    }
    
    private VBox createPaymentMethods() {
        VBox paymentSection = new VBox(15);
        
        Label title = new Label("Metode Pembayaran");
        title.setFont(Font.font("System", FontWeight.BOLD, 20));
        title.setTextFill(Color.web("#9957B3"));
        
        HBox methodsBox = new HBox(15);
        
        qrisBox = createPaymentMethodBox("QRIS", "qris");
        mobileBankingBox = createPaymentMethodBox("Mobile Banking", "mobile-banking");
        
        methodsBox.getChildren().addAll(qrisBox, mobileBankingBox);
        
        paymentSection.getChildren().addAll(title, methodsBox);
        
        return paymentSection;
    }
    
    private VBox createPaymentMethodBox(String name, String id) {
        VBox box = new VBox(10);
        box.setPadding(new Insets(30));
        box.setAlignment(Pos.CENTER);
        box.setPrefWidth(200);
        box.setPrefHeight(100);
        box.setStyle("-fx-border-color: #EAECF0; -fx-border-width: 2; -fx-border-radius: 8; -fx-background-radius: 8; -fx-cursor: hand;");
        
        Label label = new Label(name);
        label.setFont(Font.font("System", FontWeight.BOLD, 16));
        
        box.getChildren().add(label);
        
        box.setOnMouseClicked(e -> {
            selectedPayment = id;
            // Reset all boxes
            qrisBox.setStyle("-fx-border-color: #EAECF0; -fx-border-width: 2; -fx-border-radius: 8; -fx-background-radius: 8; -fx-cursor: hand;");
            mobileBankingBox.setStyle("-fx-border-color: #EAECF0; -fx-border-width: 2; -fx-border-radius: 8; -fx-background-radius: 8; -fx-cursor: hand;");
            // Highlight selected
            box.setStyle("-fx-border-color: #008733; -fx-border-width: 2; -fx-border-radius: 8; -fx-background-radius: 8; -fx-cursor: hand;");
        });
        
        return box;
    }
    
    private void showPaymentConfirmation() {
        if (selectedPayment == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Pilih Metode Pembayaran");
            alert.setHeaderText("Metode pembayaran belum dipilih");
            alert.setContentText("Silakan pilih metode pembayaran (QRIS atau Mobile Banking) terlebih dahulu.");
            alert.showAndWait();
            return;
        }
        
        if ("qris".equals(selectedPayment)) {
            navigationController.showQRISPayment(bookings);
        } else if ("mobile-banking".equals(selectedPayment)) {
            navigationController.showMobileBankingPayment(bookings);
        }
    }
}
