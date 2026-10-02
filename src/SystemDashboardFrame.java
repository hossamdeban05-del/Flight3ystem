import javax.swing.*;
import java.awt.*;

public class SystemDashboardFrame extends JFrame {

    public SystemDashboardFrame() {

        setTitle("Dashboard");
        setSize(500,350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JTextArea textArea = new JTextArea();

        textArea.setEditable(false);

        textArea.setText(
                "Airline Booking System Loaded Successfully\n\n" +
                        "Existing classes integrated:\n" +
                        "- Booking\n" +
                        "- Customer\n" +
                        "- Payment\n" +
                        "- FlightManager\n" +
                        "- Ticket\n" +
                        "- Reservation\n"
        );

        add(new JScrollPane(textArea), BorderLayout.CENTER);

        setVisible(true);
    }
}