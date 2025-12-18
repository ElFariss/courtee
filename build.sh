#!/bin/bash

# Courtee Java Build Script (Maven-based)

echo "================================"
echo "Courtee Java Build Script"
echo "================================"

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "✗ Maven is not installed. Please install Maven first."
    exit 1
fi

# Clean and compile with Maven
echo "Cleaning previous build..."
mvn clean

echo ""
echo "Compiling source files with Maven..."
mvn compile

if [ $? -eq 0 ]; then
    echo "✓ Compilation successful"
else
    echo "✗ Compilation failed"
    exit 1
fi

echo ""
echo "================================"
echo "Build completed successfully!"
echo "================================"
echo ""
echo "To run tests:"
echo "  mvn test"
echo ""
echo "To run the application:"
echo "  mvn javafx:run"
