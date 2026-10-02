import java.util.ArrayList;
import java.util.Scanner;

public class Agent extends User {
    private String department;
    private double commission;// commission percentage

    public Agent(String userId, String username, String password, String name, String email, String contactInfo,
                 String department, double commission) {
        super(userId, username, password, name, email, contactInfo);
        this.department = department;
        this.commission = commission;
    }

    //(Getters)
    public String getDepartment() {
        return department;
    }

    public double getCommission() {
        return commission;
    }

    //(Setters)
    public void setDepartment(String department) {
        this.department = department;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    @Override
    public String getRole() {
        return "AGENT";
    }

    @Override
    public void showMenu(Scanner sc, ArrayList<User> users) {
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("\n==========================================");
            System.out.println("   AGENT DASHBOARD");
            System.out.println("   Welcome, " + getName() + "!");
            System.out.println("==========================================");
            System.out.println("  1. Manage Flights");
            System.out.println("  2. View All Bookings");
            System.out.println("  3. Create Booking for Customer");
            System.out.println("  4. Update Profile");
            System.out.println("  5. View My Info");
            System.out.println("  6. Logout");
            System.out.println("==========================================");
            System.out.print("Enter your choice: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    // Implemented by Person 2 (Flight Management)
                    System.out.println("[Manage Flights - Handled by Flight Management module]");
                    break;

                case "2":
                    // Implemented by Person 3 (Booking Management)
                    System.out.println("[All Bookings - Handled by Booking Management module]");
                    break;

                case "3":
                    // Implemented by Person 3 (Booking Management)
                    System.out.println("[Create Booking for Customer - Handled by Booking Management module]");
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

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Department : " + department);
        System.out.println("Commission : " + commission + "%");
    }

    @Override
    public String toFileString() {
        return super.toFileString() + "|" + department + "|" + commission;
    }
}