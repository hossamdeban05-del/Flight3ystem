import java.io.*;
import java.util.ArrayList;

public class FileManager {
    private static final String USERS_FILE = "data/users.txt";

    // Read all users from users.txt and return them as an ArrayList
    public static ArrayList<User> loadUsers() {
        ArrayList<User> users = new ArrayList<>();
        File file = new File(USERS_FILE);
        if (!file.exists()) {
            return users;
        }  // return empty list if file doesn't exist yet
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                User user = parseUserFromLine(line);
                if (user != null) {
                    users.add(user);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading users file: " + e.getMessage());
        }
        return users;
    }

    // Write all users back to users.txt (overwrites the file)
    public static void saveUsers(ArrayList<User> users) {
        // Create the data folder if it doesn't exist
        File dir = new File("data");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE))) {
            for (User user : users) {
                writer.write(user.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving users file: " + e.getMessage());
        }
    }

    // Converts one line from the file back into the correct User object
    private static User parseUserFromLine(String line) {
        String[] parts = line.split("\\|");
        if (parts.length < 7) {
            System.out.println("Warning: Skipping malformed line in users.txt: " + line);
            return null;
        }
        String role = parts[0];
        String userId = parts[1];
        String username = parts[2];
        String password = parts[3];
        String name = parts[4];
        String email = parts[5];
        String contactInfo = parts[6];
        try {
            switch (role) {
                case "CUSTOMER":
                    String address = parts.length > 7 ? parts[7] : "";
                    String preference = parts.length > 8 ? parts[8] : "Economy";
                    return new Customer(userId, username, password, name, email, contactInfo, address, preference);

                case "AGENT":
                    String department = parts.length > 7 ? parts[7] : "";
                    double commission = parts.length > 8 ? Double.parseDouble(parts[8]) : 0.0;
                    return new Agent(userId, username, password, name, email, contactInfo, department, commission);

                case "ADMIN":
                    int secLevel = parts.length > 7 ? Integer.parseInt(parts[7]) : 1;
                    return new Administrator(userId, username, password, name, email, contactInfo, secLevel);

                default:
                    System.out.println("Warning: Unknown role '" + role + "' â skipping.");
                    return null;
            }
        } catch (NumberFormatException e) {
            System.out.println("Warning: Could not parse a number in line: " + line);
            return null;
        }
    }
}
