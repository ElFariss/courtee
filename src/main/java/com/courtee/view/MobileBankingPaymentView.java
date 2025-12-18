package com.courtee.view;

import java.util.List;

import com.courtee.controller.NavigationController;
import com.courtee.model.Booking;
import com.courtee.service.BookingService;
import com.courtee.service.PaymentService;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

public class MobileBankingPaymentView extends BorderPane {

   private NavigationController navigationController;
   private List<Booking> bookings;
   private PaymentService paymentService;
   private BookingService bookingService;
   private int timeLeft = 3600;
   private Label timerLabel;
   private boolean guideOpen = true;

   public MobileBankingPaymentView(NavigationController navigationController, List<Booking> bookings) {
      this.navigationController = navigationController;
      this.bookings = bookings;
      this.paymentService = new PaymentService();
      this.bookingService = new BookingService();
      initUI();
      startTimer();
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
      backButton.setOnAction(e -> showLeaveConfirmation());

      Label title = new Label("Pembayaran");
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
      content.setStyle("-fx-alignment: center;");

      Label instructions = new Label(
            "Silahkan gunakan aplikasi mobile banking pilihan anda untuk menyelesaikan pembayaran.");
      instructions.setWrapText(true);
      instructions.setFont(Font.font(14));

      double total = calculateTotal();
      Label amountLabel = new Label("Nominal yang harus dibayarkan Rp " + String.format("%,.2f", total));
      amountLabel.setTextFill(Color.web("#9957B3"));

      timerLabel = new Label("Selesaikan pembayaran dalam " + formatTime(timeLeft));
      timerLabel.setTextFill(Color.web("#9957B3"));

      VBox vaBox = createVirtualAccountBox();
      VBox guideBox = createPaymentGuide();
      Button confirmButton = createConfirmButton();

      content.getChildren().addAll(instructions, amountLabel, timerLabel, vaBox, guideBox, confirmButton);

      return content;
   }

   private VBox createVirtualAccountBox() {
      VBox vaBox = new VBox(15);
      vaBox.setStyle(
            "-fx-background-color: #f9fafb; -fx-border-color: #EAECF0; -fx-border-width: 1; -fx-padding: 20; -fx-background-radius: 8; -fx-border-radius: 8;");

      HBox bankInfo = new HBox(10);
      bankInfo.setAlignment(Pos.CENTER_LEFT);

      Region icon = new Region();
      icon.setStyle(
            "-fx-background-color: linear-gradient(to right, orange, pink); -fx-background-radius: 20; -fx-min-width: 40; -fx-min-height: 40;");

      Label bankName = new Label("SeaBank");
      bankName.setFont(Font.font("System", FontWeight.BOLD, 14));
      bankName.setTextFill(Color.web("#9957B3"));

      Label separator = new Label("|");
      separator.setTextFill(Color.web("#6B7280"));

      Label vaType = new Label("Virtual Account");
      vaType.setTextFill(Color.web("#6B7280"));

      bankInfo.getChildren().addAll(icon, bankName, separator, vaType);

      HBox vaNumber = new HBox(20);
      vaNumber.setAlignment(Pos.CENTER_LEFT);

      Label number = new Label(paymentService.generateVirtualAccount());
      number.setFont(Font.font("System", FontWeight.BOLD, 24));
      number.setStyle("-fx-letter-spacing: 2;");

      Button copyButton = new Button("📋");
      copyButton.setStyle("-fx-background-color: transparent; -fx-font-size: 18; -fx-cursor: hand;");
      copyButton.setTooltip(new Tooltip("Copy to clipboard"));

      vaNumber.getChildren().addAll(number, copyButton);

      vaBox.getChildren().addAll(bankInfo, vaNumber);

      return vaBox;
   }

   private VBox createPaymentGuide() {
      VBox guideBox = new VBox(10);

      Button toggleButton = new Button("Panduan Pembayaran " + (guideOpen ? "▲" : "▼"));
      toggleButton.setStyle("-fx-background-color: transparent; -fx-font-weight: bold; -fx-cursor: hand;");
      toggleButton.setOnAction(e -> {
         guideOpen = !guideOpen;
         toggleButton.setText("Panduan Pembayaran " + (guideOpen ? "▲" : "▼"));
         updateGuideVisibility(guideBox);
      });

      VBox steps = new VBox(5);
      steps.setStyle("-fx-padding: 10 0 0 20;");

      String[] guideSteps = {
            "1. Buka aplikasi mobile banking anda",
            "2. Pilih menu transaksi",
            "3. Pilih virtual account",
            "4. Pilih SeaBank sebagai bank tujuan",
            "5. Masukkan nomor virtual account",
            "6. Masukan nominal sesuai tagihan anda",
            "7. Selesaikan transfer",
            "8. Tekan tombol konfirmasi di bawah"
      };

      for (String step : guideSteps) {
         Label stepLabel = new Label(step);
         stepLabel.setTextFill(Color.web("#6B7280"));
         steps.getChildren().add(stepLabel);
      }

      guideBox.getChildren().addAll(toggleButton, steps);

      return guideBox;
   }

   private void updateGuideVisibility(VBox guideBox) {
      if (guideBox.getChildren().size() > 1) {
         guideBox.getChildren().get(1).setVisible(guideOpen);
         guideBox.getChildren().get(1).setManaged(guideOpen);
      }
   }

   private Button createConfirmButton() {
      Button confirmButton = new Button("Konfirmasi");
      confirmButton.setStyle(
            "-fx-background-color: #9957B3; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 16; -fx-padding: 15 30; -fx-cursor: hand;");
      confirmButton.setMaxWidth(Double.MAX_VALUE);
      confirmButton.setOnAction(e -> handleConfirmPayment());

      return confirmButton;
   }

   private void handleConfirmPayment() {
      // Confirm bookings and mark time slots as unavailable
      boolean success = bookingService.confirmBookings(bookings);

      if (success) {
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
         alert.setTitle("Pembayaran Berhasil");
         alert.setHeaderText("Order anda berhasil diproses!");
         alert.setContentText("Anda akan diarahkan ke home. Anda dapat mengakses dashboard untuk mengecek pesanan.");

         alert.showAndWait();
         navigationController.showHome();
      } else {
         Alert alert = new Alert(Alert.AlertType.ERROR);
         alert.setTitle("Pembayaran Gagal");
         alert.setHeaderText("Terjadi kesalahan!");
         alert.setContentText("Gagal memproses pembayaran. Silakan coba lagi.");

         alert.showAndWait();
      }
   }

   private void showLeaveConfirmation() {
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setTitle("Konfirmasi");
      alert.setHeaderText("Batalkan Pembayaran?");
      alert.setContentText("Apakah Anda yakin ingin membatalkan pembayaran?");

      alert.showAndWait().ifPresent(response -> {
         if (response == ButtonType.OK) {
            navigationController.showCheckout(bookings);
         }
      });
   }

   private void startTimer() {
      Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
         if (timeLeft > 0) {
            timeLeft--;
            timerLabel.setText("Selesaikan pembayaran dalam " + formatTime(timeLeft));
         }
      }));
      timeline.setCycleCount(Timeline.INDEFINITE);
      timeline.play();
   }

   private String formatTime(int seconds) {
      int mins = seconds / 60;
      int secs = seconds % 60;
      return String.format("%02d:%02d", mins, secs);
   }

   private double calculateTotal() {
      double subtotal = bookings.stream().mapToDouble(Booking::getPrice).sum();
      return subtotal * 1.12;
   }
}
