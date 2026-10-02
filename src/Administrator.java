import java.util.ArrayList;
import java.util.Scanner;

public class Administrator extends User {
    private int securityLevel;  // 1 (lowest) to 5 (highest)

    public Administrator(String userId, String username, String password, String name, String email, String contactInfo, int securityLevel) {
        super(userId, username, password, name, email, contactInfo);
        this.securityLevel = securityLevel;
    }

    // Getter&&Setter
    public int getSecurityLevel() {
        return securityLevel;
    }

    public void setSecurityLevel(int level) {
        this.securityLevel = level;
    }

    @Override
    public String getRole() {
        return "ADMIN";
    }

    @Override
    public void showMenu(Scanner sc, ArrayList<User> users) {
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("\n==========================================");
            System.out.println("   ADMIN DASHBOARD");
            System.out.println("   Welcome, " + getName() + "!");
            System.out.println("==========================================");
            System.out.println("  1. Create New User Account");
            System.out.println("  2. View All Users");
            System.out.println("  3. Delete a User Account");
            System.out.println("  4. Update My Profile");
            System.out.println("  5. View My Info");
            System.out.println("  6. Logout");
            System.out.println("==========================================");
            System.out.print("Enter your choice: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    createUser(sc, users);
                    break;

                case "2":
                    viewAllUsers(users);
                    break;

                case "3":
                    deleteUser(sc, users);
                    break;

                case "4":
                    updateProfile(sc, users);
                    break;

                case "5":
                    displayInfo();
                    break;

                case "6":
                    logout();
                    loggedIn = false;
                    break;

                default:
                    System.out.println("Invalid option. Please enter a number from 1 to 6.");
            }
        }
    }

    // Admin can create any type of user account
    private void createUser(Scanner sc, ArrayList<User> users) {
        System.out.println("\n--- Create New User Account ---");
        System.out.println("Select user type:");
        System.out.println("1. Customer");
        System.out.println("2. Agent");
        System.out.println("3. Administrator");
        System.out.print("Your choice: ");
        String typeChoice = sc.nextLine().trim();
        if (!typeChoice.equals("1") && !typeChoice.equals("2") && !typeChoice.equals("3")) {
            System.out.println("Invalid choice. Returning to menu.");
            return;
        }
        System.out.print("Username: ");
        String username = sc.nextLine().trim();
        if (username.isEmpty()) {
            System.out.println("Error: Username cannot be empty.");
            return;
        }
        for (User u : users) { // Make sure no duplicate usernames exist
            if (u.getUsername().equalsIgnoreCase(username)) {
                System.out.println("Error: Username '" + username + "' is already taken.");
                return;
            }
        }
        System.out.print("Password: ");
        String password = sc.nextLine().trim();
        if (!isValidPassword(password)) {
            System.out.println("Error: Password must be at least 6 characters and contain both letters and numbers.");
            return;
        }
        System.out.print("Full Name: ");
        String name = sc.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Error: Name cannot be empty.");
            return;
        }
        System.out.print("Email: ");
        String email = sc.nextLine().trim();
        System.out.print("Contact Number: ");
        String contact = sc.nextLine().trim();

        switch (typeChoice) {
            case "1":
                System.out.print("Address: ");
                String address = sc.nextLine().trim();
                System.out.println("Seat Preference: 1. Economy  2. Business  3. First Class");
                System.out.print("Choose (1-3): ");
                String prefChoice = sc.nextLine().trim();
                String preference;
                switch (prefChoice) {
                    case "2":
                        preference = "Business";
                        break;
                    case "3":
                        preference = "First Class";
                        break;
                    default:
                        preference = "Economy";
                        break;
                }
                String customerId = "C" + String.format("%03d", users.size() + 1);
                Customer newCustomer = new Customer(customerId, username, password, name, email, contact, address, preference);
                users.add(newCustomer);
                FileManager.saveUsers(users);
                System.out.println("Customer account created successfully. ID: " + customerId);
                break;

            case "2":
                System.out.print("Department: ");
                String dept = sc.nextLine().trim();
                System.out.print("Commission percentage: ");
                double comm = 0;
                try {
                    comm = Double.parseDouble(sc.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid value. Commission set to 0.");
                }
                String agentId = "A" + String.format("%03d", users.size() + 1);
                Agent newAgent = new Agent(agentId, username, password, name, email, contact, dept, comm);
                users.add(newAgent);
                FileManager.saveUsers(users);
                System.out.println("Agent account created successfully. ID: " + agentId);
                break;

            case "3":
                System.out.print("Security Level (1-5): ");
                int secLevel = 1;
                try {
                    secLevel = Integer.parseInt(sc.nextLine().trim());
                    if (secLevel < 1 || secLevel > 5) {
                        System.out.println("Security level must be between 1 and 5. Setting to 1.");
                        secLevel = 1;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid value. Security level set to 1.");
                }
                String adminId = "ADM" + String.format("%03d", users.size() + 1);
                Administrator newAdmin = new Administrator(adminId, username, password, name, email, contact, secLevel);
                users.add(newAdmin);
                FileManager.saveUsers(users);
                System.out.println("Administrator account created successfully. ID: " + adminId);
                break;
        }
    }

    // Display a table of all registered users
    private void viewAllUsers(ArrayList<User> users) {
        System.out.println("\n--- All Registered Users (" + users.size() + " total) ---");

        if (users.isEmpty()) {
            System.out.println("No users found.");
            return;
        }

        System.out.printf("%-10s %-15s %-20s %-25s %-10s%n", "ID", "Username", "Name", "Email", "Role");
        System.out.println("-".repeat(82));

        for (User u : users) {
            System.out.printf("%-10s %-15s %-20s %-25s %-10s%n", u.getUserId(), u.getUsername(), u.getName(), u.getEmail(), u.getRole());
        }
    }

    // Remove a user account by username
    private void deleteUser(Scanner sc, ArrayList<User> users) {
        System.out.print("\nEnter the username of the account to delete: ");
        String username = sc.nextLine().trim();

        if (username.equalsIgnoreCase(getUsername())) {
            System.out.println("Error: You cannot delete your own account.");
            return;
        }

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equalsIgnoreCase(username)) {
                System.out.print("Are you sure you want to delete '" + username + "'? (yes/no): ");
                String confirm = sc.nextLine().trim();

                if (confirm.equalsIgnoreCase("yes")) {
                    users.remove(i);
                    FileManager.saveUsers(users);
                    System.out.println("User '" + username + "' has been deleted successfully.");
                } else {
                    System.out.println("Deletion cancelled.");
                }
                return;
            }
        }
        System.out.println("Error: No user found with username '" + username + "'.");
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Security Level : " + securityLevel);
    }

    @Override
    public String toFileString() {
        return super.toFileString() + "|" + securityLevel;
    }
}