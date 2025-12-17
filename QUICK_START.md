# Quick Start Guide - Courtee Java Application

## Prerequisites

Before running the application, ensure you have:

- ✅ **Java JDK 11 or higher** installed
- ✅ **Maven 3.6 or higher** installed
- ✅ **JavaFX 17** (automatically downloaded by Maven)

### Check Your Java Version

```bash
java -version
javac -version
```

You should see Java 11 or higher.

### Check Maven Installation

```bash
mvn -version
```

If Maven is not installed, install it:

**On Ubuntu/Debian:**
```bash
sudo apt-get update
sudo apt-get install maven
```

**On macOS:**
```bash
brew install maven
```

**On Windows:**
Download from https://maven.apache.org/download.cgi

---

## Running the Application

### Option 1: Using Maven (Recommended)

1. **Navigate to the project directory:**
   ```bash
   cd /home/parasite/Work/Tugas/APS/courtee/java-implementation
   ```

2. **Clean and compile:**
   ```bash
   mvn clean compile
   ```

3. **Run the application:**
   ```bash
   mvn javafx:run
   ```

The JavaFX application window will open showing the Courtee home page.

### Option 2: Using the Build Script

If you don't have Maven or want a simpler build:

1. **Make the script executable:**
   ```bash
   chmod +x build.sh
   ```

2. **Run the build script:**
   ```bash
   ./build.sh
   ```

Note: This only compiles the code. To run, you'll need JavaFX libraries.

---

## Running Unit Tests

### Run All Tests

```bash
mvn test
```

Expected output:
```
Tests run: 34, Failures: 0, Errors: 0, Skipped: 0
```

### Run Specific Test

```bash
mvn test -Dtest=VenueTest
mvn test -Dtest=CourtTest
mvn test -Dtest=DataRepositoryTest
```

### View Test Reports

After running tests, view the detailed report at:
```
target/surefire-reports/
```

---

## Application Usage Guide

### 1. Home Page

When you launch the application, you'll see:
- **Header** with the Courtee logo
- **Search section** (filters are for display only)
- **6 Venue cards** showing different sports facilities

**Action:** Click on any venue card to view details

### 2. Venue Detail Page

After clicking a venue, you'll see:
- **Venue information** (name, location)
- **Date selector** dropdown
- **Available courts** (2 courts for Longfield Sport Center)
- **Time slot grid** for each court

**Actions:**
- Select a date from the dropdown
- Click on **available time slots** (they turn green when selected)
- Gray slots are unavailable
- Click **"Lanjutkan ke Pembayaran"** button when you've selected slots

### 3. Checkout Page

After selecting time slots and clicking checkout:
- View your **selected bookings** (venue, court, date, time)
- See **price breakdown**:
  - Subtotal
  - Tax (12%)
  - Total
- Select **payment method**:
  - QRIS
  - Mobile Banking

**Action:** Click payment method (border turns green), then click confirmation button

### 4. Confirmation

A success dialog will appear confirming your booking.

---

## Troubleshooting

### Problem: "mvn: command not found"

**Solution:** Install Maven (see Prerequisites section)

### Problem: "JavaFX runtime components are missing"

**Solution:** 
The JavaFX dependencies should be automatically downloaded by Maven. If not:
```bash
mvn clean install
mvn javafx:run
```

### Problem: "JAVA_HOME not set"

**Solution:**
```bash
# Find Java installation
which java

# Set JAVA_HOME (add to ~/.bashrc or ~/.zshrc)
export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64
export PATH=$JAVA_HOME/bin:$PATH
```

### Problem: Tests fail to run

**Solution:**
Ensure JUnit dependencies are downloaded:
```bash
mvn clean install
mvn test
```

---

## Building a Standalone JAR

To create a distributable JAR file:

```bash
mvn clean package
```

The JAR file will be created in:
```
target/courtee-java-1.0-SNAPSHOT.jar
```

To run the JAR:
```bash
java --module-path /path/to/javafx-sdk/lib \
     --add-modules javafx.controls,javafx.fxml \
     -jar target/courtee-java-1.0-SNAPSHOT.jar
```

---

## Project Structure Overview

```
java-implementation/
├── src/
│   ├── main/java/com/courtee/
│   │   ├── CourteeApp.java          ← Main entry point
│   │   ├── model/                    ← Data models
│   │   ├── view/                     ← UI views
│   │   ├── controller/               ← Navigation logic
│   │   └── utils/                    ← Utilities
│   └── test/java/com/courtee/        ← Unit tests
├── pom.xml                           ← Maven configuration
├── build.sh                          ← Build script
└── README.md                         ← Full documentation
```

---

## Key Files to Explore

### Main Application
- `src/main/java/com/courtee/CourteeApp.java` - Application entry point

### Views
- `src/main/java/com/courtee/view/HomeView.java` - Home page
- `src/main/java/com/courtee/view/VenueDetailView.java` - Venue details
- `src/main/java/com/courtee/view/CheckoutView.java` - Checkout page

### Models
- `src/main/java/com/courtee/model/Venue.java` - Venue data model
- `src/main/java/com/courtee/model/Court.java` - Court data model
- `src/main/java/com/courtee/model/TimeSlot.java` - Time slot model
- `src/main/java/com/courtee/model/Booking.java` - Booking model

### Sample Data
- `src/main/java/com/courtee/utils/DataRepository.java` - Hardcoded sample data

---

## Common Maven Commands

| Command | Description |
|---------|-------------|
| `mvn clean` | Remove build artifacts |
| `mvn compile` | Compile source code |
| `mvn test` | Run unit tests |
| `mvn package` | Create JAR file |
| `mvn javafx:run` | Run JavaFX application |
| `mvn clean install` | Full build with dependencies |

---

## Getting Help

### Documentation Files
- **README.md** - Comprehensive documentation
- **IMPLEMENTATION_SUMMARY.md** - Technical details
- **VERIFICATION_REPORT.txt** - Component verification
- **QUICK_START.md** - This file

### Code Comments
All Java files include inline comments explaining key functionality.

### Test Files
Unit tests serve as usage examples for each component.

---

## Next Steps

1. ✅ Run the application and explore all features
2. ✅ Run unit tests to verify functionality
3. ✅ Review the code to understand the implementation
4. ✅ Modify sample data in `DataRepository.java` to experiment
5. ✅ Extend functionality (add new features, improve UI)

---

## Success Criteria

You'll know the application is working correctly when:

- ✅ Home page displays 6 venues
- ✅ Clicking a venue navigates to detail page
- ✅ Time slots can be selected (turn green)
- ✅ Checkout button is enabled when slots are selected
- ✅ Checkout page shows correct calculations
- ✅ Payment method can be selected
- ✅ Confirmation dialog appears after payment
- ✅ All 34 unit tests pass

---

**Happy Coding! 🚀**

If you encounter any issues, check the troubleshooting section or review the full documentation in README.md.
