import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginFrame() {

        setTitle("Airline Booking System");
        setSize(400,250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3,2,10,10));

        panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(e -> login());

        panel.add(new JLabel("Username"));
        panel.add(usernameField);

        panel.add(new JLabel("Password"));
        panel.add(passwordField);

        panel.add(new JLabel());
        panel.add(loginButton);

        add(panel);

        setVisible(true);
    }

    private void login() {

        String username = usernameField.getText().trim();
        String password = String.valueOf(passwordField.getPassword());

        if(username.isEmpty() || password.isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Please enter your data.");
            return;
        }

        dispose();

        new SystemDashboardFrame();
    }
}