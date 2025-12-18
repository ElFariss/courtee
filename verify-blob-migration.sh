#!/bin/bash
# Verification script for BLOB image storage migration

echo "================================================"
echo "Courtee BLOB Image Storage Migration Verification"
echo "================================================"
echo ""

# Check if database exists
if [ -f "courtee.db" ]; then
    echo "✓ Database file exists: courtee.db"
    echo ""
    
    # Check database schema
    echo "Checking database schema..."
    echo ""
    
    echo "1. Venues table columns:"
    sqlite3 courtee.db "PRAGMA table_info(venues);" | grep -E "(image_name|image_data)"
    
    echo ""
    echo "2. Courts table columns:"
    sqlite3 courtee.db "PRAGMA table_info(courts);" | grep -E "(image_name|image_data)"
    
    echo ""
    echo "3. Venues with image data:"
    sqlite3 courtee.db "SELECT venue_id, name, image_name, CASE WHEN image_data IS NOT NULL THEN 'YES (Size: ' || length(image_data) || ' bytes)' ELSE 'NO' END as has_blob FROM venues;"
    
    echo ""
    echo "4. Courts with image data:"
    sqlite3 courtee.db "SELECT court_id, name, image_name, CASE WHEN image_data IS NOT NULL THEN 'YES (Size: ' || length(image_data) || ' bytes)' ELSE 'NO' END as has_blob FROM courts;"
    
    echo ""
    echo "5. Total venues: $(sqlite3 courtee.db 'SELECT COUNT(*) FROM venues;')"
    echo "6. Total courts: $(sqlite3 courtee.db 'SELECT COUNT(*) FROM courts;')"
    
else
    echo "✗ Database file not found: courtee.db"
    echo ""
    echo "Please run the application first to create and initialize the database:"
    echo "  ./build.sh"
    echo "  or"
    echo "  mvn clean compile exec:java"
fi

echo ""
echo "================================================"
echo "Verification complete!"
echo "================================================"
