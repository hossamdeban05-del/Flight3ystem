// =========================
// AirlineSystemGUI.java
// =========================

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class AirlineSystemGUI extends JFrame {

    JTable table;
    DefaultTableModel model;

    public AirlineSystemGUI() {

        setTitle("Airline Reservation System");
        setSize(900, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        model = new DefaultTableModel(
                new String[]{"Flight ID", "From", "To", "Seats", "Price"}, 0
        );

        table = new JTable(model);

        table.setBackground(new Color(220, 235, 250));

        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel panel = new JPanel(new GridLayout(2, 4, 10, 10));

        JButton showBtn = new JButton("Show Flights");
        JButton searchBtn = new JButton("Search Flights");
        JButton bookBtn = new JButton("Book Flight");
        JButton viewBtn = new JButton("View Booking");
        JButton cancelBtn = new JButton("Cancel Booking");
        JButton revenueBtn = new JButton("Revenue Report");
        JButton refreshBtn = new JButton("Refresh");
        JButton exitBtn = new JButton("Exit");

        panel.add(showBtn);
        panel.add(searchBtn);
        panel.add(bookBtn);
        panel.add(viewBtn);
        panel.add(cancelBtn);
        panel.add(revenueBtn);
        panel.add(refreshBtn);
        panel.add(exitBtn);

        add(panel, BorderLayout.SOUTH);

        showBtn.addActionListener(e -> showFlights());
        searchBtn.addActionListener(e -> searchFlights());
        bookBtn.addActionListener(e -> bookFlight());
        viewBtn.addActionListener(e -> viewBooking());
        cancelBtn.addActionListener(e -> cancelBooking());
        revenueBtn.addActionListener(e -> revenueReport());
        refreshBtn.addActionListener(e -> showFlights());
        exitBtn.addActionListener(e -> System.exit(0));

        showFlights();
    }

    // =========================
    // SHOW FLIGHTS
    // =========================

    void showFlights() {

        model.setRowCount(0);

        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM flights")) {

            while (rs.next()) {

                model.addRow(new Object[]{
                        rs.getString("flightId"),
                        rs.getString("origin"),
                        rs.getString("destination"),
                        rs.getInt("seats"),
                        "$" + rs.getDouble("price")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =========================
    // SEARCH FLIGHTS
    // =========================

    void searchFlights() {

        String fromCity = JOptionPane.showInputDialog(this, "From:");
        String toCity = JOptionPane.showInputDialog(this, "To:");

        if (fromCity == null || toCity == null) return;

        model.setRowCount(0);

        try (Connection conn = DatabaseConnection.connect()) {

            PreparedStatement pstmt = conn.prepareStatement(
                    "SELECT * FROM flights WHERE lower(origin)=lower(?) AND lower(destination)=lower(?)"
            );

            pstmt.setString(1, fromCity);
            pstmt.setString(2, toCity);

            ResultSet rs = pstmt.executeQuery();

            boolean found = false;

            while (rs.next()) {

                found = true;

                model.addRow(new Object[]{
                        rs.getString("flightId"),
                        rs.getString("origin"),
                        rs.getString("destination"),
                        rs.getInt("seats"),
                        "$" + rs.getDouble("price")
                });
            }

            if (!found) {

                JOptionPane.showMessageDialog(this,
                        "No flights found!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =========================
    // BOOK FLIGHT
    // =========================

    void bookFlight() {

        String flightID = JOptionPane.showInputDialog(this,
                "Enter Flight ID:");

        String name = JOptionPane.showInputDialog(this,
                "Passenger Name:");

        if (flightID == null || name == null ||
                flightID.isEmpty() || name.isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Please enter valid data.");

            return;
        }

        try (Connection conn = DatabaseConnection.connect()) {

            PreparedStatement checkStmt =
                    conn.prepareStatement(
                            "SELECT seats FROM flights WHERE flightId = ?"
                    );

            checkStmt.setString(1, flightID);

            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getInt("seats") > 0) {

                PreparedStatement updateStmt =
                        conn.prepareStatement(
                                "UPDATE flights SET seats = seats - 1 WHERE flightId = ?"
                        );

                updateStmt.setString(1, flightID);
                updateStmt.executeUpdate();

                PreparedStatement insertStmt =
                        conn.prepareStatement(
                                "INSERT INTO bookings(passengerName, flightId) VALUES(?,?)"
                        );

                insertStmt.setString(1, name);
                insertStmt.setString(2, flightID);

                insertStmt.executeUpdate();

                Statement stmt = conn.createStatement();

                ResultSet bookingRS =
                        stmt.executeQuery(
                                "SELECT MAX(bookingId) AS id FROM bookings"
                        );

                int bookingID = 0;

                if (bookingRS.next()) {
                    bookingID = bookingRS.getInt("id");
                }

                JOptionPane.showMessageDialog(this,
                        "Booking Successful!\nBooking ID: " + bookingID);

                showFlights();

            } else {

                JOptionPane.showMessageDialog(this,
                        "No available seats!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =========================
    // VIEW BOOKING
    // =========================

    void viewBooking() {

        String input = JOptionPane.showInputDialog(this,
                "Enter Booking ID:");

        if (input == null || input.isEmpty()) return;

        try (Connection conn = DatabaseConnection.connect()) {

            PreparedStatement pstmt =
                    conn.prepareStatement(
                            "SELECT * FROM bookings WHERE bookingId=?"
                    );

            pstmt.setInt(1, Integer.parseInt(input));

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {

                JOptionPane.showMessageDialog(this,
                        "Passenger: " + rs.getString("passengerName") +
                                "\nFlight: " + rs.getString("flightId"));

            } else {

                JOptionPane.showMessageDialog(this,
                        "Booking Not Found!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =========================
    // CANCEL BOOKING
    // =========================

    void cancelBooking() {

        String input = JOptionPane.showInputDialog(this,
                "Enter Booking ID:");

        if (input == null || input.isEmpty()) return;

        try (Connection conn = DatabaseConnection.connect()) {

            PreparedStatement findStmt =
                    conn.prepareStatement(
                            "SELECT * FROM bookings WHERE bookingId=?"
                    );

            findStmt.setInt(1, Integer.parseInt(input));

            ResultSet rs = findStmt.executeQuery();

            if (rs.next()) {

                String flightId = rs.getString("flightId");

                PreparedStatement deleteStmt =
                        conn.prepareStatement(
                                "DELETE FROM bookings WHERE bookingId=?"
                        );

                deleteStmt.setInt(1, Integer.parseInt(input));
                deleteStmt.executeUpdate();

                PreparedStatement updateSeats =
                        conn.prepareStatement(
                                "UPDATE flights SET seats = seats + 1 WHERE flightId=?"
                        );

                updateSeats.setString(1, flightId);
                updateSeats.executeUpdate();

                JOptionPane.showMessageDialog(this,
                        "Booking Cancelled!");

                showFlights();

            } else {

                JOptionPane.showMessageDialog(this,
                        "Booking Not Found!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =========================
    // REVENUE REPORT
    // =========================

    void revenueReport() {

        StringBuilder report = new StringBuilder();

        double totalRevenue = 0;

        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM flights")) {

            while (rs.next()) {

                int sold =
                        rs.getInt("totalSeats") - rs.getInt("seats");

                double revenue =
                        sold * rs.getDouble("price");

                totalRevenue += revenue;

                report.append(
                                rs.getString("flightId"))
                        .append(" → $")
                        .append(revenue)
                        .append("\\n");
            }

            report.append("\\nTOTAL REVENUE = $")
                    .append(totalRevenue);

            JOptionPane.showMessageDialog(this,
                    report.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}