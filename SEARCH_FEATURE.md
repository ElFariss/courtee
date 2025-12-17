# Search Feature Documentation

## ✨ Feature: Working Search Filters

The search functionality now allows users to filter venues by **Area (Daerah)** and **Sport Type (Jenis Olahraga)**.

---

## 🔍 Search Filters

### 1. Area Filter (Daerah)
- **Type**: Text Input
- **Placeholder**: "Daerah (e.g., Jakarta Barat, Jakarta Pusat)"
- **Functionality**: 
  - Real-time filtering as you type
  - Case-insensitive search
  - Partial matching (e.g., "Jakarta" matches all Jakarta areas)
  - Searches in venue location field

**Examples:**
- Search "Jakarta Barat" → Shows all venues in Jakarta Barat
- Search "jakarta" → Shows all venues in any Jakarta area
- Search "pusat" → Shows venues in Jakarta Pusat

### 2. Sport Type Filter (Jenis Olahraga)
- **Type**: Dropdown ComboBox
- **Options**:
  - Semua Jenis Olahraga (All)
  - Lapangan Sepak Bola (Football Field)
  - Lapangan Badminton (Badminton Court)
  - Lapangan Tennis (Tennis Court)
  - Lapangan Padel (Padel Court)
  - Lapangan Mini Soccer (Mini Soccer Field)

**Functionality**:
- Exact match on venue type
- Auto-filters when selection changes

---

## 🎯 How It Works

### User Flow

1. **Open Home Page**
   - See search section at the top with filters
   - All 6 venues displayed by default

2. **Filter by Area**
   - Type "Jakarta Barat" in the area field
   - Venues automatically filter to show only Jakarta Barat venues
   - Type more to narrow down further

3. **Filter by Sport Type**
   - Select "Lapangan Badminton" from dropdown
   - Only badminton courts are shown

4. **Combine Filters**
   - Use both area and sport type together
   - Example: "Jakarta Barat" + "Lapangan Badminton"
   - Shows only badminton courts in Jakarta Barat (2 venues)

5. **Reset Filters**
   - Click "Reset Filter" button
   - All filters cleared
   - All 6 venues shown again

---

## 🏗️ Technical Implementation

### Service Layer

**ISearchService.java**
```java
public interface ISearchService {
    List<Venue> searchVenues(String area, String sportType);
    List<Venue> filterByArea(List<Venue> venues, String area);
    List<Venue> filterBySportType(List<Venue> venues, String sportType);
}
```

**SearchService.java**
- Implements filtering logic
- Uses Java Streams for efficient filtering
- Case-insensitive area search
- Exact match for sport type

### View Updates

**HomeView.java**
- Added search fields as class members
- Real-time filtering on text input
- Event listeners on both filters
- Dynamic venue grid update
- "No results" message when no venues match

---

## 📊 Search Examples

### Example 1: Search by Area Only
```
Input: Area = "Jakarta Barat", Sport Type = "Semua Jenis Olahraga"
Result: 6 venues (all venues in dataset)
```

### Example 2: Search by Sport Type Only
```
Input: Area = "", Sport Type = "Lapangan Badminton"
Result: 2 venues (Balistic Badminton, Balistic Badminton 2)
```

### Example 3: Combined Search
```
Input: Area = "Jakarta Barat", Sport Type = "Lapangan Sepak Bola"
Result: 1 venue (Longfield Sport Center)
```

### Example 4: No Results
```
Input: Area = "Bandung", Sport Type = "Semua Jenis Olahraga"
Result: Empty list with message "Tidak ada venue yang sesuai dengan pencarian Anda."
```

---

## 🎨 UI Components

### Search Section
- **Title**: "Cari Venue Olahraga" (purple, bold, 28px)
- **Area Input**: Text field with placeholder
- **Sport Type Dropdown**: ComboBox with predefined options
- **Date Picker**: ComboBox (for display, not yet functional)
- **Search Button**: Purple button "Cari Venue"
- **Reset Button**: Gray button "Reset Filter"

### Venue Grid
- Shows filtered results in 3-column grid
- Dynamically updates on filter change
- Shows "No results" message when empty

---

## 🧪 Testing

### Unit Tests (SearchServiceTest.java)

**Total Tests: 8**

1. `testSearchByArea()` - Filter by area only
2. `testSearchBySportType()` - Filter by sport type only
3. `testSearchByAreaAndSportType()` - Combined filters
4. `testSearchNoResults()` - No matching venues
5. `testFilterByAreaCaseInsensitive()` - Case-insensitive search
6. `testFilterByAreaPartialMatch()` - Partial matching
7. `testFilterBySportTypeExact()` - Exact sport type match

### Run Tests
```bash
cd java-implementation
mvn test -Dtest=SearchServiceTest
```

Expected: All 8 tests pass

---

## 🔄 Filter Behavior

### Real-Time Filtering
- Area field: Filters as you type (on text change)
- Sport type: Filters on selection change
- Both work together (AND operation)

### Reset Functionality
- Clears area text field
- Sets sport type back to "Semua Jenis Olahraga"
- Shows all venues again

### Empty State
- When no venues match filters
- Shows message: "Tidak ada venue yang sesuai dengan pencarian Anda."
- Message is gray, centered in grid

---

## 📝 Venue Data

### Available Locations
- Jakarta Barat (6 venues)
- Jakarta Pusat (can be added to test)

### Available Sport Types
1. Lapangan Sepak Bola (1 venue)
2. Lapangan Badminton (2 venues)
3. Lapangan Tennis (1 venue)
4. Lapangan Padel (1 venue)
5. Lapangan Mini Soccer (1 venue)

---

## 🚀 How to Use

### For Users

1. **Run the application**
   ```bash
   cd java-implementation
   mvn javafx:run
   ```

2. **Filter by area**
   - Type location in the first field
   - Results update automatically

3. **Filter by sport**
   - Select from dropdown
   - Results update immediately

4. **Reset**
   - Click "Reset Filter" to see all venues

### For Developers

**To add more filter options:**
1. Add sport types to `sportTypeCombo.getItems()` in HomeView
2. Ensure DataRepository has venues with matching types
3. Add test cases in SearchServiceTest

**To modify filter logic:**
1. Update SearchService implementation
2. Adjust filter methods as needed
3. Update tests accordingly

---

## ✅ Features Checklist

- ✅ Area text search (case-insensitive)
- ✅ Sport type dropdown filter
- ✅ Combined filtering (area + sport)
- ✅ Real-time updates
- ✅ Reset functionality
- ✅ Empty state message
- ✅ Service layer implementation
- ✅ Unit tests (8 tests)
- ✅ User-friendly UI
- ✅ Responsive grid layout

---

## 🎯 Use Cases

### Use Case 1: Find Badminton Courts
```
User Story: As a badminton player, I want to find badminton courts
Action: Select "Lapangan Badminton" from sport type dropdown
Result: Shows 2 badminton venues
```

### Use Case 2: Find Venues in Jakarta Barat
```
User Story: As a user in West Jakarta, I want nearby venues
Action: Type "Jakarta Barat" in area field
Result: Shows all 6 venues (all are in Jakarta Barat)
```

### Use Case 3: Find Specific Court Type in Area
```
User Story: As a tennis player in Jakarta Barat, I want tennis courts
Action: Type "Jakarta Barat" + Select "Lapangan Tennis"
Result: Shows Singhasari Tennis Club
```

---

## 🔧 Future Enhancements

Potential improvements:
1. Add price range filter
2. Add availability date filter (make date picker functional)
3. Add sorting options (price, name, distance)
4. Add favorite/bookmark feature
5. Add map view for location-based search
6. Add autocomplete for area field
7. Add multi-select for sport types
8. Add advanced filters (facilities, ratings)

---

**Version**: 2.1.0  
**Last Updated**: December 17, 2024  
**Status**: ✅ Fully Functional
