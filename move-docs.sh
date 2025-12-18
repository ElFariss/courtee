#!/bin/bash

# Script to move documentation files to dev_history folder

echo "Moving documentation files to dev_history..."

# Move BLOB migration docs
mv BLOB_MIGRATION_STATUS.md dev_history/blob_migration/ 2>/dev/null && echo "✓ Moved BLOB_MIGRATION_STATUS.md"
mv BLOB_MIGRATION_COMPLETE.md dev_history/blob_migration/ 2>/dev/null && echo "✓ Moved BLOB_MIGRATION_COMPLETE.md"
mv BLOB_STORAGE_GUIDE.md dev_history/blob_migration/ 2>/dev/null && echo "✓ Moved BLOB_STORAGE_GUIDE.md"
mv BLOB_STMT_CLOSURE_FIX.md dev_history/blob_migration/ 2>/dev/null && echo "✓ Moved BLOB_STMT_CLOSURE_FIX.md"
mv CONNECTION_PATTERN_FIX.md dev_history/blob_migration/ 2>/dev/null && echo "✓ Moved CONNECTION_PATTERN_FIX.md"
mv CONNECTION_FIX_SUMMARY.md dev_history/blob_migration/ 2>/dev/null && echo "✓ Moved CONNECTION_FIX_SUMMARY.md"

# Move booking system docs
mv BOOKING_FEATURE_IMPLEMENTATION.md dev_history/booking_system/ 2>/dev/null && echo "✓ Moved BOOKING_FEATURE_IMPLEMENTATION.md"
mv BOOKING_CONFIRMATION_FIX.md dev_history/booking_system/ 2>/dev/null && echo "✓ Moved BOOKING_CONFIRMATION_FIX.md"
mv BOOKING_QUICK_REF.md dev_history/booking_system/ 2>/dev/null && echo "✓ Moved BOOKING_QUICK_REF.md"

# Move testing docs
mv TEST_FIXES.md dev_history/testing/ 2>/dev/null && echo "✓ Moved TEST_FIXES.md"

# Move database docs
mv SQLITE_JDBC_FIX.md dev_history/database/ 2>/dev/null && echo "✓ Moved SQLITE_JDBC_FIX.md"

# Move summary docs
mv UPDATE_SUMMARY.md dev_history/summaries/ 2>/dev/null && echo "✓ Moved UPDATE_SUMMARY.md"
mv UPDATE_FINAL_SUMMARY.md dev_history/summaries/ 2>/dev/null && echo "✓ Moved UPDATE_FINAL_SUMMARY.md"

echo ""
echo "Done! All documentation files moved to dev_history/"
echo "README.md remains in root directory."
