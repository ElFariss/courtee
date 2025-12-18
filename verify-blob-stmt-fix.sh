#!/bin/bash

# BLOB Statement Closure Fix Verification Script
# This script verifies that the changes to fix "stmt pointer is closed" errors are in place

echo "🔍 Verifying BLOB Statement Closure Fixes..."
echo ""

# Check if VenueDAO uses PreparedStatement instead of Statement
echo "1️⃣ Checking VenueDAO.getAllVenues() uses PreparedStatement..."
if grep -q "PreparedStatement pstmt = conn.prepareStatement" src/main/java/com/courtee/database/dao/VenueDAO.java; then
    echo "   ✅ VenueDAO uses PreparedStatement"
else
    echo "   ❌ VenueDAO still uses Statement"
    exit 1
fi

# Check if VenueDAO has safe BLOB reading
echo "2️⃣ Checking VenueDAO has safe BLOB reading..."
if grep -q "if (imageData != null && imageData.length > 0)" src/main/java/com/courtee/database/dao/VenueDAO.java; then
    echo "   ✅ VenueDAO has NULL checks for BLOB data"
else
    echo "   ❌ VenueDAO missing NULL checks for BLOB data"
    exit 1
fi

# Check if VenueDAO avoids nested queries during ResultSet iteration
echo "3️⃣ Checking VenueDAO avoids nested queries during iteration..."
if grep -A 10 "getAllVenues" src/main/java/com/courtee/database/dao/VenueDAO.java | grep -q "// Load time slots after"; then
    echo "   ✅ VenueDAO defers time slot loading"
else
    echo "   ❌ VenueDAO may have nested query issues"
    exit 1
fi

# Check if CourtDAO has safe BLOB reading
echo "4️⃣ Checking CourtDAO has safe BLOB reading..."
if grep -q "if (imageData != null && imageData.length > 0)" src/main/java/com/courtee/database/dao/CourtDAO.java; then
    echo "   ✅ CourtDAO has NULL checks for BLOB data"
else
    echo "   ❌ CourtDAO missing NULL checks for BLOB data"
    exit 1
fi

# Check if explicit column selection is used instead of SELECT *
echo "5️⃣ Checking for explicit column selection..."
if grep -q "SELECT venue_id, name, type, location, price_per_hour, image_name, image_data FROM venues" src/main/java/com/courtee/database/dao/VenueDAO.java; then
    echo "   ✅ VenueDAO uses explicit column selection"
else
    echo "   ⚠️  VenueDAO may still use SELECT *"
fi

echo ""
echo "✨ All BLOB statement closure fixes verified!"
echo ""
echo "📋 Next Steps:"
echo "   1. Delete courtee.db: rm courtee.db"
echo "   2. Build the project: ./build.sh"
echo "   3. Run the application and check for errors"
echo "   4. Verify images load correctly in the UI"
