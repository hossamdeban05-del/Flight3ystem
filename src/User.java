import java.util.ArrayList;
import java.util.Scanner;/* Abstract base class for all users in the system
 Customer, Agent, and Administrator all extend this class*/

public abstract class User {
    private String userId;
    private final String username;
    private String password;
    private String name;
    private String email;
    private String contactInfo;

    public User(String userId, String username, String password,
                String name, String email, String contactInfo) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.contactInfo = contactInfo;
    }//[GETERS]

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    // (Setterssss)
    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public abstract String getRole(); // Each subclass must return its role name

    public abstract void showMenu(Scanner sc, ArrayList<User> users);   // Each subclass shows its own dashboard menu

    /* Password Validation (Password must be at least 6 characters and contain both letters and numbers)*/
    public static boolean isValidPassword(String password) {
        if (password.length() < 6) {
            return false;
        }
        boolean hasLetter = false;
        boolean hasDigit = false;
        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) hasLetter = true;
            if (Character.isDigit(c)) hasDigit = true;
        }
        return hasLetter && hasDigit;
    }

    // Shared Profile Update (used by all subclasses)
    public void updateProfile(Scanner sc, ArrayList<User> users) {
        System.out.println("\n--- Update Profile ---");
        System.out.println("1. Change Name");
        System.out.println("2. Change Email");
        System.out.println("3. Change Contact Info");
        System.out.println("4. Change Password");
        System.out.println("5. Back");
        System.out.print("Your choice: ");
        String choice = sc.nextLine().trim();

        switch (choice) {
            case "1":
                System.out.print("Enter new name: ");
                String newName = sc.nextLine().trim();
                if (newName.isEmpty()) {
                    System.out.println("Error: Name cannot be empty.");
                } else {
                    setName(newName);
                    FileManager.saveUsers(users);
                    System.out.println("Name updated successfully.");
                }
                break;

            case "2":
                System.out.print("Enter new email: ");
                String newEmail = sc.nextLine().trim();
                if (newEmail.isEmpty()) {
                    System.out.println("Error: Email cannot be empty.");
                } else {
                    setEmail(newEmail);
                    FileManager.saveUsers(users);
                    System.out.println("Email updated successfully.");
                }
                break;

            case "3":
                System.out.print("Enter new contact info: ");
                String newContact = sc.nextLine().trim();
                setContactInfo(newContact);
                FileManager.saveUsers(users);
                System.out.println("Contact info updated successfully.");
                break;

            case "4":
                System.out.print("Enter current password: ");
                String currentPass = sc.nextLine().trim();
                if (!currentPass.equals(password)) {
                    System.out.println("Error: Incorrect current password.");
                    break;
                }
                System.out.print("Enter new password: ");
                String newPass = sc.nextLine().trim();
                if (!isValidPassword(newPass)) {
                    System.out.println("Error: Password must be at least 6 characters and contain both letters and numbers.");
                    break;
                }
                setPassword(newPass);
                FileManager.saveUsers(users);
                System.out.println("Password changed successfully.");
                break;

            case "5":
                break;

            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    public void logout() {
        System.out.println("\nLogged out successfully. See you soon, " + name + "!");
    }

    // Print this user's info to the console
    public void displayInfo() {
        System.out.println("\n--- My Profile ---");
        System.out.println("User ID  : " + userId);
        System.out.println("Username : " + username);
        System.out.println("Name     : " + name);
        System.out.println("Email    : " + email);
        System.out.println("Contact  : " + contactInfo);
        System.out.println("Role     : " + getRole());
    }

    /* Converts this user to a single line for saving in users.txt Subclasses override and append their extra fields*/
    public String toFileString() {
        return getRole() + "|" + userId + "|" + username + "|" + password + "|"
                + name + "|" + email + "|" + contactInfo;
    }
}
