import java.util.ArrayList;
import java.util.Scanner;
public class Customer extends User {
    private String address;
    private String seatPreference;  // Economy, Business, or First Class

    public Customer(String userId, String username, String password,String name, String email, String contactInfo,
                    String address, String seatPreference) {
        super(userId, username, password, name, email, contactInfo);
        this.address = address;
        this.seatPreference = seatPreference; }
    //[Getters]
    public String getAddress()        { return address; }
    public String getSeatPreference() { return seatPreference; }
    // Setters
    public void setAddress(String address)               { this.address        = address; }
    public void setSeatPreference(String seatPreference) { this.seatPreference = seatPreference; }
    @Override
    public String getRole() {
        return "CUSTOMER";
    }
    @Override
    public void showMenu(Scanner sc, ArrayList<User> users) {
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("\n==========================================");
            System.out.println("   CUSTOMER DASHBOARD");
            System.out.println("   Welcome, " + getName() + "!");
            System.out.println("==========================================");
            System.out.println("  1. Search Flights");
            System.out.println("  2. My Bookings");
            System.out.println("  3. Update Profile");
            System.out.println("  4. View My Info");
            System.out.println("  5. Logout");
            System.out.println("==========================================");
            System.out.print("Enter your choice: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    // This feature is implemented by the Flight Management module (Person 2)
                    System.out.println("[Flight Search - Handled by Flight Management module]");
                    sc.nextLine();
                    break;

                case "2":
                    // This feature is implemented by the Booking Management module (Person 3)
                    System.out.println("[My Bookings - Handled by Booking Management module]");
                    sc.nextLine();
                    break;

                case "3":
                    updateProfile(sc, users);
                    break;

                case "4":
                    displayInfo();
                    break;

                case "5":
                    logout();
                    loggedIn = false;
                    break;

                default:
                    System.out.println("Invalid option. Please enter a number from 1 to 5.");}}}
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Address    : " + address);
        System.out.println("Preference : " + seatPreference);}
    // Saves all customer fields to the file line
    @Override
    public String toFileString() {
        return super.toFileString() + "|" + address + "|" + seatPreference;
    }}