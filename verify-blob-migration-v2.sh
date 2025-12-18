#!/bin/bash

# BLOB Migration Verification Script
# This script verifies that the BLOB migration is complete and working correctly

echo "=================================="
echo "BLOB Migration Verification Script"
echo "=================================="
echo ""

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Test counter
TESTS_PASSED=0
TESTS_FAILED=0

# Function to print test result
print_result() {
    if [ $1 -eq 0 ]; then
        echo -e "${GREEN}✓ PASSED${NC}: $2"
        ((TESTS_PASSED++))
    else
        echo -e "${RED}✗ FAILED${NC}: $2"
        ((TESTS_FAILED++))
    fi
}

# Test 1: Check if project compiles
echo "Test 1: Checking if project compiles..."
./build.sh > /dev/null 2>&1
print_result $? "Project compilation"
echo ""

# Test 2: Check if database exists
echo "Test 2: Checking if database exists..."
if [ -f "courtee.db" ]; then
    print_result 0 "Database file exists"
else
    echo -e "${YELLOW}! WARNING${NC}: Database file not found. Run the application first."
    print_result 1 "Database file exists"
fi
echo ""

# Test 3: Check database schema
echo "Test 3: Checking database schema..."
if [ -f "courtee.db" ]; then
    # Check if venues table has image_data column
    VENUES_SCHEMA=$(sqlite3 courtee.db ".schema venues" 2>/dev/null | grep -c "image_data BLOB")
    if [ $VENUES_SCHEMA -eq 1 ]; then
        print_result 0 "Venues table has image_data BLOB column"
    else
        print_result 1 "Venues table has image_data BLOB column"
    fi
    
    # Check if courts table has image_data column
    COURTS_SCHEMA=$(sqlite3 courtee.db ".schema courts" 2>/dev/null | grep -c "image_data BLOB")
    if [ $COURTS_SCHEMA -eq 1 ]; then
        print_result 0 "Courts table has image_data BLOB column"
    else
        print_result 1 "Courts table has image_data BLOB column"
    fi
else
    print_result 1 "Cannot check schema - database not found"
    print_result 1 "Cannot check schema - database not found"
fi
echo ""

# Test 4: Check if venues are populated
echo "Test 4: Checking if venues are populated..."
if [ -f "courtee.db" ]; then
    VENUE_COUNT=$(sqlite3 courtee.db "SELECT COUNT(*) FROM venues;" 2>/dev/null)
    if [ -n "$VENUE_COUNT" ] && [ $VENUE_COUNT -gt 0 ]; then
        print_result 0 "Database has $VENUE_COUNT venues"
    else
        print_result 1 "Database has venues"
    fi
else
    print_result 1 "Cannot check venues - database not found"
fi
echo ""

# Test 5: Check if venues have BLOB data
echo "Test 5: Checking if venues have BLOB image data..."
if [ -f "courtee.db" ]; then
    BLOBS_WITH_DATA=$(sqlite3 courtee.db "SELECT COUNT(*) FROM venues WHERE image_data IS NOT NULL AND LENGTH(image_data) > 0;" 2>/dev/null)
    TOTAL_VENUES=$(sqlite3 courtee.db "SELECT COUNT(*) FROM venues;" 2>/dev/null)
    
    if [ -n "$BLOBS_WITH_DATA" ] && [ -n "$TOTAL_VENUES" ] && [ $BLOBS_WITH_DATA -eq $TOTAL_VENUES ]; then
        print_result 0 "All $TOTAL_VENUES venues have BLOB image data"
        
        # Show details
        echo ""
        echo "  Venue Image Sizes:"
        sqlite3 courtee.db "SELECT '  ' || name || ': ' || LENGTH(image_data) || ' bytes' FROM venues;" 2>/dev/null
    else
        print_result 1 "All venues have BLOB image data (found $BLOBS_WITH_DATA/$TOTAL_VENUES with data)"
    fi
else
    print_result 1 "Cannot check BLOB data - database not found"
fi
echo ""

# Test 6: Verify a BLOB is valid image data
echo "Test 6: Verifying BLOB data is valid image format..."
if [ -f "courtee.db" ]; then
    # Extract first few bytes to check magic number
    MAGIC=$(sqlite3 courtee.db "SELECT HEX(SUBSTR(image_data, 1, 4)) FROM venues LIMIT 1;" 2>/dev/null)
    
    # Check for JPEG (FFD8FFE0 or FFD8FFE1) or PNG (89504E47) magic numbers
    if [[ $MAGIC == FFD8* ]] || [[ $MAGIC == 89504E47* ]]; then
        print_result 0 "BLOB data has valid image header (JPEG/PNG)"
    else
        print_result 1 "BLOB data has valid image header (got: $MAGIC)"
    fi
else
    print_result 1 "Cannot verify BLOB format - database not found"
fi
echo ""

# Test 7: Run unit tests
echo "Test 7: Running unit tests..."
mvn test -q > /tmp/test-output.txt 2>&1
if [ $? -eq 0 ]; then
    print_result 0 "Unit tests passed"
else
    print_result 1 "Unit tests passed"
    echo "  See /tmp/test-output.txt for details"
fi
echo ""

# Test 8: Check for required Java files
echo "Test 8: Checking for required Java source files..."
REQUIRED_FILES=(
    "src/main/java/com/courtee/database/DatabaseConnection.java"
    "src/main/java/com/courtee/database/dao/VenueDAO.java"
    "src/main/java/com/courtee/database/dao/CourtDAO.java"
    "src/main/java/com/courtee/model/Venue.java"
    "src/main/java/com/courtee/model/Court.java"
    "src/main/java/com/courtee/view/HomeView.java"
)

ALL_FILES_EXIST=true
for file in "${REQUIRED_FILES[@]}"; do
    if [ ! -f "$file" ]; then
        echo "  Missing: $file"
        ALL_FILES_EXIST=false
    fi
done

if $ALL_FILES_EXIST; then
    print_result 0 "All required source files exist"
else
    print_result 1 "All required source files exist"
fi
echo ""

# Test 9: Check for image resources
echo "Test 9: Checking for image resources..."
IMAGE_FILES=(
    "src/main/resources/soccer-field.jpg"
    "src/main/resources/padel-court.jpg"
    "src/main/resources/badminton-court.jpg"
    "src/main/resources/mini-soccer.jpg"
    "src/main/resources/tennis-court.jpg"
)

ALL_IMAGES_EXIST=true
for img in "${IMAGE_FILES[@]}"; do
    if [ ! -f "$img" ]; then
        echo "  Missing: $img"
        ALL_IMAGES_EXIST=false
    fi
done

if $ALL_IMAGES_EXIST; then
    print_result 0 "All image resources exist"
else
    print_result 1 "All image resources exist"
fi
echo ""

# Summary
echo "=================================="
echo "VERIFICATION SUMMARY"
echo "=================================="
echo -e "Tests Passed: ${GREEN}$TESTS_PASSED${NC}"
echo -e "Tests Failed: ${RED}$TESTS_FAILED${NC}"
echo ""

if [ $TESTS_FAILED -eq 0 ]; then
    echo -e "${GREEN}✓ ALL TESTS PASSED!${NC}"
    echo "The BLOB migration is complete and working correctly."
    exit 0
else
    echo -e "${RED}✗ SOME TESTS FAILED${NC}"
    echo "Please review the failed tests above."
    exit 1
fi
