# Courtee - Java JavaFX Implementation

This is a Java JavaFX implementation of the Courtee sports venue booking application, converted from the Next.js/React version.

## Overview

Courtee is a sports venue booking system that allows users to browse venues, view available courts and time slots, and make bookings. This Java implementation provides the same core functionality using JavaFX for the user interface.

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
│   │   │           │   └── CheckoutView.java
│   │   │           ├── controller/     # Controllers
│   │   │           │   └── NavigationController.java
│   │   │           ├── utils/          # Utilities
│   │   │           │   ├── DataRepository.java
│   │   │           │   └── CurrencyFormatter.java
│   │   │           └── CourteeApp.java # Main application
│   │   └── resources/
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
- Display header with brand logo and navigation
- Search section with filters for area, sport type, and date
- Grid of recommended venues with details
- Footer with links and social media icons

### Venue Detail View
- Display venue information and hero image
- Date selector
- List of available courts with images and descriptions
- Time slots with prices (available/unavailable states)
- Click to select time slots (turns green when selected)
- Checkout button (enabled only when slots are selected)

### Checkout View
- Display all selected bookings
- Show subtotal, tax (12%), and total
- Payment method selection (QRIS or Mobile Banking)
- Confirmation dialog on payment

## Requirements

- Java 11 or higher
- Maven 3.6 or higher
- JavaFX 17 or higher (included in dependencies)

## Building and Running

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

4. **Package the application:**
   ```bash
   mvn package
   ```

### Running from IDE

Import the project as a Maven project in your IDE (IntelliJ IDEA, Eclipse, etc.) and run the `CourteeApp` class.

## Testing

The project includes comprehensive unit tests for all model classes and utilities:

- **VenueTest**: Tests for venue creation, time slots, and getters/setters
- **CourtTest**: Tests for court creation, time slots, and properties
- **TimeSlotTest**: Tests for time slot availability and pricing
- **BookingTest**: Tests for booking creation and data management
- **DataRepositoryTest**: Tests for venue and court data retrieval
- **CurrencyFormatterTest**: Tests for currency formatting

Run all tests with:
```bash
mvn test
```

## Data Models

### Venue
- ID, name, type, location
- Price per hour
- Image path
- Available time slots

### Court
- ID, name, description
- Image path
- List of time slots with availability

### TimeSlot
- Time range
- Price
- Availability status

### Booking
- Venue name, court name
- Date and time
- Price

## Color Scheme

The application uses a consistent color palette matching the original design:
- Primary Purple: `#9957B3`
- Success Green: `#008733`
- Gray Text: `#6B7280`
- Border: `#EAECF0`
- Background: White and `#f9fafb`

## Notes

- This is a simplified implementation for educational purposes
- Images are represented as placeholders (text labels)
- All data is hardcoded in the DataRepository class
- No database or backend integration
- Payment is simulated with a confirmation dialog

## Future Enhancements

Possible improvements for production use:
- Integrate with a real database
- Add actual image loading
- Implement user authentication
- Add booking persistence
- Integrate with payment gateways
- Add calendar date picker
- Implement search functionality
- Add booking history

## License

This is a simple assignment project for educational purposes.
