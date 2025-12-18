package com.courtee.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
   private static String DB_URL = "jdbc:sqlite:courtee.db";
   private static boolean initialized = false;

   // For testing purposes - allow custom database path
   public static void initialize(String dbPath) {
      DB_URL = "jdbc:sqlite:" + dbPath;
      initialized = false; // Reset initialization flag
   }

   public static Connection getConnection() throws SQLException {
      Connection conn = DriverManager.getConnection(DB_URL);
      if (!initialized) {
         initializeDatabase(conn);
         initialized = true;
      }
      return conn;
   }

   // For testing - create tables explicitly
   public static void createTables(Connection conn) throws SQLException {
      try (Statement stmt = conn.createStatement()) {
         // Create venues table
         stmt.execute("CREATE TABLE IF NOT EXISTS venues (" +
               "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
               "venue_id TEXT UNIQUE NOT NULL, " +
               "name TEXT NOT NULL, " +
               "type TEXT NOT NULL, " +
               "location TEXT NOT NULL, " +
               "price_per_hour REAL NOT NULL, " +
               "image_name TEXT NOT NULL, " +
               "image_data BLOB" +
               ")");

         // Create courts table
         stmt.execute("CREATE TABLE IF NOT EXISTS courts (" +
               "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
               "court_id TEXT UNIQUE NOT NULL, " +
               "venue_id TEXT NOT NULL, " +
               "name TEXT NOT NULL, " +
               "description TEXT, " +
               "image_name TEXT, " +
               "image_data BLOB, " +
               "FOREIGN KEY (venue_id) REFERENCES venues(venue_id)" +
               ")");

         // Create time_slots table
         stmt.execute("CREATE TABLE IF NOT EXISTS time_slots (" +
               "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
               "court_id TEXT NOT NULL, " +
               "time TEXT NOT NULL, " +
               "price REAL NOT NULL, " +
               "available INTEGER NOT NULL DEFAULT 1, " +
               "FOREIGN KEY (court_id) REFERENCES courts(court_id)" +
               ")");

         // Create bookings table
         stmt.execute("CREATE TABLE IF NOT EXISTS bookings (" +
               "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
               "venue_name TEXT NOT NULL, " +
               "court_name TEXT NOT NULL, " +
               "booking_date TEXT NOT NULL, " +
               "time_slot TEXT NOT NULL, " +
               "price REAL NOT NULL, " +
               "payment_method TEXT, " +
               "payment_status TEXT, " +
               "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
               ")");

         // Create venue_time_slots table
         stmt.execute("CREATE TABLE IF NOT EXISTS venue_time_slots (" +
               "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
               "venue_id TEXT NOT NULL, " +
               "time_slot TEXT NOT NULL, " +
               "FOREIGN KEY (venue_id) REFERENCES venues(venue_id)" +
               ")");
      }
   }

   private static void initializeDatabase(Connection conn) throws SQLException {
      createTables(conn);
   }

   // For testing - close a specific connection
   public static void close(Connection conn) {
      if (conn != null) {
         try {
            if (!conn.isClosed()) {
               conn.close();
            }
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }
   }
}
