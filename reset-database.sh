#!/bin/bash
# Script to reset the Courtee database
# This will delete the existing database and allow the app to create a fresh one on next run

echo "🔄 Courtee Database Reset Script"
echo "================================"
echo ""

DB_FILE="courtee.db"

if [ -f "$DB_FILE" ]; then
    echo "📦 Found existing database: $DB_FILE"
    read -p "⚠️  This will DELETE all current data. Continue? (y/N): " -n 1 -r
    echo ""
    
    if [[ ! $REPLY =~ ^[Yy]$ ]]; then
        echo "❌ Database reset cancelled."
        exit 1
    fi
    
    echo "🗑️  Removing existing database..."
    rm "$DB_FILE"
    echo "✅ Database removed successfully!"
    echo ""
    echo "📋 Next steps:"
    echo "   1. Build the application: ./build.sh"
    echo "   2. Run the application: mvn javafx:run"
    echo "   The database will be recreated with fresh data on first run."
else
    echo "ℹ️  No database file found at: $DB_FILE"
    echo "Database will be created on first application run."
fi
