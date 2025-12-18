# Courtee - Java JavaFX Implementation

This is a Java JavaFX implementation of the Courtee sports venue booking application, converted from the Next.js/React version.

## Overview

Courtee is a sports venue booking system that allows users to browse venues, view available courts and time slots, and make bookings. This Java implementation provides the same core functionality using JavaFX for the user interface and SQLite for data storage.

## Project Structure

```
java-implementation/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── courtee/
│   │   │           ├── model/          # Data models
│   │   │           │   ├── Venue.java
│   │   │           │   ├── Court.java
│   │   │           │   ├── TimeSlot.java
│   │   │           │   └── Booking.java
│   │   │           ├── view/           # UI views
│   │   │           │   ├── HomeView.java
│   │   │           │   ├── VenueDetailView.java
│   │   │           │   ├── CheckoutView.java
│   │   │           │   ├── QRISPaymentView.java
│   │   │           │   └── MobileBankingPaymentView.java
│   │   │           ├── service/        # Business logic
│   │   │           │   ├── BookingService.java
│   │   │           │   ├── PaymentService.java
│   │   │           │   ├── SearchService.java
│   │   │           │   ├── VenueService.java
│   │   │           │   └── interfaces...
│   │   │           ├── controller/     # Controllers
│   │   │           │   └── NavigationController.java
│   │   │           ├── utils/          # Utilities
│   │   │           │   ├── DataRepository.java
│   │   │           │   └── CurrencyFormatter.java
│   │   │           └── CourteeApp.java # Main application
│   │   └── resources/              # Images and resources
│   │       ├── QR.png
│   │       ├── soccer-field.jpg
│   │       ├── tennis-court.jpg
│   │       ├── badminton-court.jpg
│   │       ├── badminton-court-2.jpg
│   │       ├── mini-soccer.jpg
│   │       └── padel-court.jpg
│   └── test/
│       └── java/
│           └── com/
│               └── courtee/            # Unit tests
│                   ├── VenueTest.java
│                   ├── CourtTest.java
│                   ├── TimeSlotTest.java
│                   ├── BookingTest.java
│                   ├── DataRepositoryTest.java
│                   └── CurrencyFormatterTest.java
└── pom.xml
```

## Features

### Home View
- Header with logo and navigation
- Search section with filters for area, sport type, and date
- Grid of recommended venues with images loaded from database (BLOB)
- Footer with links and social media icons
- Dynamic venue search and filtering functionality

### Venue Detail View
- Displays venue information with images from database
- Date selector with **date-specific slot availability**
- List of available courts with descriptions and images
- Time slots with pricing (available/unavailable status)
- Click to select time slots (turns green when selected)
- Checkout button (enabled only when slots are selected)
- **Slots booked for one date remain available for other dates**

### Checkout View
- Displays all selected bookings in a summary
- Shows subtotal, tax (12%), and total calculation
- Payment method selection (QRIS or Mobile Banking)
- Navigation to specific payment views

### Payment Views
- **QRIS Payment View**: Displays QR code image (QR.png) from resources with countdown timer
- **Mobile Banking Payment View**: Shows bank transfer details and instructions
- Confirmation dialogs to prevent accidental navigation
- Payment success feedback
- **Payment confirmation saves bookings and marks time slots as unavailable for the specific date only**

## Key Features

### Date-Specific Booking System
- **Per-Date Availability**: Time slots are checked for availability on a per-date basis
- **Independent Dates**: Booking a slot on one date does not block it for other dates
- **Dynamic Refresh**: Slot availability updates automatically when changing dates
- **Smart Filtering**: System queries bookings table to determine date-specific availability

### BLOB Image Storage
- **No Hardcoded References**: All images loaded from database
- **Auto-Initialization**: Images converted from resources to BLOB on first run
- **Optimal Performance**: Images cached in memory for fast access
- **Scalable**: Easy to add/update images through database

### Integrated Booking System
- **Complete Booking Flow**: Venue selection → Time slot selection → Checkout → Payment → Confirmation
- **Date-Aware Management**: Slot status tracked per date, not globally
- **Persistent Storage**: All bookings saved to SQLite database
- **Visual Feedback**: Booked slots shown in gray, selected in green, available in purple

## Requirements

- Java 11 or higher
- Maven 3.6 or higher
- JavaFX 17 or higher (included in dependencies)
- SQLite 3.x (JDBC driver included in dependencies)

## Build and Run

### Using Maven

1. **Build the project:**
   ```bash
   mvn clean compile
   ```

2. **Run the application:**
   ```bash
   mvn javafx:run
   ```

3. **Run tests:**
   ```bash
   mvn test
   ```

4. **Reset database (removes all data):**
   ```bash
   chmod +x reset-database.sh
   ./reset-database.sh
   ```

5. **Package application:**
   ```bash
   mvn package
   ```

### Using Build Script

```bash
chmod +x build.sh
./build.sh
```

### Running from IDE

Import the project as a Maven project in your IDE (IntelliJ IDEA, Eclipse, etc.) and run the `CourteeApp` class.

## Testing

The project includes comprehensive unit tests covering models, services, and integration tests:

### Model Tests (All Passing ✓)
- **VenueTest**: Tests venue creation, time slots, and getters/setters
- **CourtTest**: Tests court creation and properties
- **TimeSlotTest**: Tests time slot availability and pricing
- **BookingTest**: Tests booking creation and data management
- **CurrencyFormatterTest**: Tests currency formatting

### Service Tests (All Passing ✓)
- **PaymentServiceTest**: Tests payment processing
- **VenueServiceTest**: Tests venue retrieval
- **SearchServiceTest**: Tests search functionality
- **BookingServiceTest**: Tests booking creation and calculations, including date-specific availability

### Integration Tests (All Passing ✓)
- **DataRepositoryTest**: Tests data repository

### Latest Test Results

```
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running com.courtee.VenueServiceTest
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0 ✓
[INFO] Running com.courtee.SearchServiceTest
[INFO] Tests run: 7, Failures: 0, Errors: 0, Skipped: 0 ✓
[INFO] Running com.courtee.service.BookingServiceTest
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0 ✓
[INFO] Running com.courtee.CurrencyFormatterTest
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0 ✓
[INFO] Running com.courtee.PaymentServiceTest
[INFO] Tests run: 4, Failures: 0, Errors: 0, Skipped: 0 ✓
[INFO] Running com.courtee.TimeSlotTest
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0 ✓
[INFO] Running com.courtee.BookingTest
[INFO] Tests run: 4, Failures: 0, Errors: 0, Skipped: 0 ✓
[INFO] Running com.courtee.CourtTest
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0 ✓
[INFO] Running com.courtee.DataRepositoryTest
[INFO] Tests run: 8, Failures: 0, Errors: 0, Skipped: 0 ✓
[INFO] Running com.courtee.VenueTest
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0 ✓
[INFO] 
[INFO] Results:
[INFO] Tests run: 53, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] BUILD SUCCESS
```

### Running Tests

Run all tests:
```bash
mvn test
```

Run specific test class:
```bash
mvn test -Dtest=BookingTest
mvn test -Dtest=VenueTest
mvn test -Dtest=PaymentServiceTest
```

Run tests with verbose output:
```bash
mvn test -X
```

**Note**: 
- All model tests (Booking, Venue, Court, TimeSlot, CurrencyFormatter) pass ✓
- Service tests pass after database initialization ✓
- Test database automatically created and cleaned up for DAO tests ✓

## Data Models

### Venue
- ID, name, type, location
- Price per hour
- Image data (BLOB)
- Available time slots

### Court
- ID, name, description
- Image data (BLOB)
- List of time slots with availability

### TimeSlot
- Time range
- Price
- Availability status

### Booking
- Venue name, court name, court ID
- Date and time
- Price
- Payment status

## Color Scheme

The application uses a consistent color palette matching the original design:
- Primary Purple: `#9957B3`
- Success Green: `#008733`
- Gray Text: `#6B7280`
- Border: `#EAECF0`
- Background: White and `#f9fafb`

## Architecture

The application follows a layered architecture pattern with clear separation of concerns:

- **Model Layer**: Data classes (Venue, Court, TimeSlot, Booking) with database IDs
- **View Layer**: JavaFX UI components (HomeView, VenueDetailView, CheckoutView, Payment Views)
- **Controller Layer**: Navigation and flow control (NavigationController)
- **Service Layer**: Business logic (BookingService, PaymentService, SearchService, VenueService)
- **DAO Layer**: Data Access Objects for database operations (VenueDAO, CourtDAO, TimeSlotDAO, BookingDAO)
- **Database Layer**: SQLite connection and initialization (DatabaseConnection, DatabaseInitializer)
- **Utility Layer**: Helper classes (CurrencyFormatter)

### Database Schema

The application uses SQLite database with the following tables:

1. **venues** - Stores venue information
   - id (INTEGER PRIMARY KEY)
   - venue_id (TEXT UNIQUE) 
   - name, type, location, price_per_hour
   - image_name (TEXT) - Image filename for reference
   - image_data (BLOB) - Binary image data

2. **courts** - Stores court information
   - id (INTEGER PRIMARY KEY)
   - court_id (TEXT UNIQUE)
   - venue_id (FOREIGN KEY)
   - name, description
   - image_name (TEXT) - Image filename for reference
   - image_data (BLOB) - Binary image data

3. **time_slots** - Stores time slot availability
   - id (INTEGER PRIMARY KEY)
   - court_id (FOREIGN KEY)
   - time, price
   - available (INTEGER) - 1 = available, 0 = unavailable (not used for date-specific logic)

4. **bookings** - Stores customer bookings
   - id (INTEGER PRIMARY KEY)
   - venue_name, court_name
   - booking_date, time_slot, price
   - payment_method, payment_status
   - created_at (TIMESTAMP)

5. **venue_time_slots** - Stores venue-level time slots
   - id (INTEGER PRIMARY KEY)
   - venue_id (FOREIGN KEY)
   - time_slot

## Date-Specific Booking Logic

The application implements intelligent date-specific booking:

1. **Time Slot Availability**: Each time slot is checked against the `bookings` table for the specific date
2. **Query Logic**: `TimeSlotDAO.isAvailableForDate(courtId, time, date)` checks if a booking exists for that combination
3. **UI Updates**: When the user changes dates, the UI refreshes to show availability for the new date
4. **Independent Dates**: A slot booked on "20 Desember 2025" remains available for "21 Desember 2025"
5. **Visual Feedback**: 
   - Available slots: Purple (`#9957B3`)
   - Selected slots: Green (`#008733`)
   - Booked slots: Gray (`#D1D5DB`) with reduced opacity

## Quick Start Guide

### Getting Started
```bash
# 1. Reset database (optional, for fresh start)
chmod +x reset-database.sh
./reset-database.sh

# 2. Build application
./build.sh

# 3. Run application
mvn javafx:run

# 4. Run tests
mvn test
```

### Testing Date-Specific Booking
1. Select a venue from home page
2. Note the selected date (e.g., "20 Desember 2025")
3. Click available time slots (they turn green)
4. Click "Lanjutkan ke Pembayaran" (Continue to Payment)
5. Select payment method (QRIS/Mobile Banking)
6. Click "Konfirmasi" (Confirm)
7. See success message
8. Return to the same venue with the same date
9. Verify the booked slots are now gray (unavailable)
10. **Change the date to "21 Desember 2025"**
11. **Verify the same time slots are available again** (purple)
12. Book a slot for the new date
13. Switch back to "20 Desember 2025"
14. Verify original bookings still show as unavailable

### Database Verification
```bash
sqlite3 courtee.db

-- Check saved bookings
SELECT * FROM bookings;

-- Check unavailable slots for a specific date
SELECT b.booking_date, b.time_slot, b.court_name 
FROM bookings b 
WHERE b.booking_date = '20 Desember 2025';

-- Exit
.quit
```

## Image Resources

The application uses images stored in `src/main/resources/`:
- **QR.png**: QR code for QRIS payment method
- **Venue images**: soccer-field.jpg, tennis-court.jpg, badminton-court.jpg, etc.

Images are loaded using JavaFX's `Image` and `ImageView` classes with proper resource path handling (`getClass().getResourceAsStream("/image.jpg")`), then converted to byte arrays and stored as BLOBs in the database.

## Future Enhancements

Possible improvements for production use:
- Migrate to MySQL/PostgreSQL for better concurrent access and scalability
- Add more venue and court images with image upload functionality
- Implement user authentication and authorization with JWT tokens
- Add user profiles with booking history and favorites
- Integration with real payment gateways (Midtrans, Xendit, Stripe)
- Add calendar date picker widget for better date selection UX
- Implement advanced search with filters (price range, rating, facilities, distance)
- Add email notifications for booking confirmations and reminders
- Implement real-time availability updates using WebSockets
- Add admin panel for venue management (CRUD operations)
- Implement booking cancellation and refund logic
- Add review and rating system for venues
- Generate PDF receipts for bookings
- Add analytics dashboard for venue owners
- Implement multi-language support (i18n)
- Add booking time limits (e.g., slots auto-release if not confirmed within 15 minutes)
- Implement recurring bookings (e.g., weekly sessions)

## Development History

All development progress documentation has been moved to the `dev_history/` folder (excluded from version control). This includes:
- BLOB migration documentation
- Booking feature implementation guides
- Bug fixes and troubleshooting guides
- Update summaries and verification reports

## License

This is a simple assignment project for educational purposes.

## Contributors

This project was developed as part of the System Analysis and Design course assignment.
