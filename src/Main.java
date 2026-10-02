// =========================
// Main.java
// =========================

import javax.swing.*;
import java.sql.Connection;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) {

        initializeDatabase();

        SwingUtilities.invokeLater(() -> {
            AirlineSystemGUI gui = new AirlineSystemGUI();
            gui.setVisible(true);
        });
    }

    private static void initializeDatabase() {

        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement()) {

            stmt.execute("""
                    CREATE TABLE IF NOT EXISTS flights(
                        flightId TEXT PRIMARY KEY,
                        origin TEXT,
                        destination TEXT,
                        seats INTEGER,
                        totalSeats INTEGER,
                        price REAL
                    )
                    """);

            stmt.execute("""
                    CREATE TABLE IF NOT EXISTS bookings(
                        bookingId INTEGER PRIMARY KEY AUTOINCREMENT,
                        passengerName TEXT,
                        flightId TEXT
                    )
                    """);

            stmt.execute("""
                    INSERT OR IGNORE INTO flights VALUES
                    ('AIU101','Cairo','Dubai',10,10,500),
                    ('EGYPT99','Cairo','London',10,10,800),
                    ('FLY202','Alexandria','Cairo',10,10,150),
                    ('SKY303','Luxor','Cairo',10,10,200),
                    ('AIR404','Aswan','Luxor',10,10,180),
                    ('JET505','Sharm-ElSheikh','Cairo',10,10,250)
                    """);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}