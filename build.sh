#!/bin/bash

# Courtee Java Build Script (without Maven)

echo "================================"
echo "Courtee Java Build Script"
echo "================================"

# Create output directories
mkdir -p build/classes
mkdir -p build/test-classes

# Compile main source files
echo "Compiling main source files..."
find src/main/java -name "*.java" > sources.txt

if [ -s sources.txt ]; then
    javac -d build/classes @sources.txt
    if [ $? -eq 0 ]; then
        echo "✓ Main source compiled successfully"
    else
        echo "✗ Main source compilation failed"
        exit 1
    fi
else
    echo "✗ No source files found"
    exit 1
fi

echo ""
echo "================================"
echo "Build completed successfully!"
echo "================================"
echo ""
echo "Note: To run tests, you need JUnit 5 dependencies."
echo "Please use Maven (mvn test) or download JUnit jars manually."
echo ""
echo "To run the application (requires JavaFX):"
echo "  java --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml -cp build/classes com.courtee.CourteeApp"

# Clean up
rm sources.txt
