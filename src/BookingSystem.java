import java.util.ArrayList;
import java.util.Scanner;

public class BookingSystem {
    private Scanner scanner;

    private ArrayList<User> users;
    private ArrayList<Booking> bookings;
    private FlightManager flightManager;
    private Transaction transaction;

    public BookingSystem() {
        users = FileManager.loadUsers();
        flightManager = new FlightManager();
        BookingFileManager BFM = new BookingFileManager();
        bookings = BFM.loadBookings();
        transaction = new Transaction();
        scanner = new Scanner(System.in);

    }

    public void start() {
        boolean running = true;
        while (running) {
            System.out.println("==================================");
            System.out.println(" WELCOME TO FLIGHT BOOKING SYSTEM ");
            System.out.println("==================================");
            System.out.println("1. Login");
            System.out.println("2. Search Flights");
            System.out.println("3. Create Booking");
            System.out.println("4. Process payment");
            System.out.println("5. Generate Ticket");
            System.out.println("6. Exit");
            System.out.print("Insert your choice: ");

            scanner.nextLine();
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                switch (choice) {
                    case 1:
                        Login();
                        break;
                    case 2:
                        searchFlights();
                        break;
                    case 3:
                        createBooking();
                        break;
                    case 4:
                        processPayment();
                        break;
                    case 5:
                        generateTicket();
                        break;
                    case 6:
                        running = false;
                        System.out.println("Thank you for using our system.");
                        break;
                    default:
                        System.out.println("Invalid! Please enter a number from 1 to 6.");
                }
            } catch(NumberFormatException e){
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
    public void Login() {

        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        boolean found = false;

        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username) && user.getPassword().equals(password)) {
                found = true;

                System.out.println("Login successful.");
                System.out.println("Welcome " + user.getName());
                user.showMenu(scanner , users);

                break;
            }
        }
        if (!found) {
            System.out.println("Invalid username or password.");
        }
    }

    public void searchFlights() {

        System.out.print("Enter Origin: ");
        String origin = scanner.nextLine();

        System.out.print("Enter Destination: ");
        String destination = scanner.nextLine();

        ArrayList<Flight> results = flightManager.searchFlights(origin, destination);

        if (results.isEmpty()) {

            System.out.println("No flights found.");

        } else {
            System.out.println("=====Available Flights=====");
            for ( Flight flight : results) {

                System.out.println(flight);
                System.out.println("-----------");
            }
        }
    }

    public void createBooking() {

        System.out.println("Booking Reference: ");
        String reference = scanner.nextLine();

        System.out.println("Customer ID: ");
        String customerID = scanner.nextLine();

        System.out.println("Flight ID: ");
        String flightId = scanner.nextLine();

        System.out.println("Seat Class (Economy/Business/First Class): ");
        String seat = scanner.nextLine();

        Booking booking = new Booking(reference, customerID, flightId, seat);

        System.out.println("Number of Passengers: ");
        try {

            int count = Integer.parseInt(scanner.nextLine());

            for (int i = 0; i < count; i++) {
                System.out.println("\nPassenger " + (i + 1));

                System.out.println("Passenger ID: ");
                String PassId = scanner.nextLine();

                System.out.println("Passenger Name: ");
                String name = scanner.nextLine();

                System.out.println("Passport Number: ");
                String passport = scanner.nextLine();

                Passenger passenger = new Passenger(PassId, name, passport, "", "");
                booking.addPassenger(passenger);
            }
        }catch (NumberFormatException e) {
            System.out.println("Invalid!");
            return;
        }

        bookings.add(booking);

        BookingFileManager BFM = new BookingFileManager();
        BFM.saveBookings(booking);
        System.out.println("\nBooking created. Reference: " + reference);
    }

    public void processPayment() {

        System.out.println("Enter Booking Reference: ");
        String reference = scanner.nextLine();
        boolean found = false;

        for ( Booking booking : bookings) {

            if (booking.getBookingReference().equals(reference)) {
                found = true;

                double total = booking.calculateTotalPrice(10000);
                System.out.println("Total Price: " + total);

                System.out.println("\nPaymentMethod: ");
                System.out.println("1. Credit Card");
                System.out.println("2. Cash");

                String choice = scanner.nextLine();
                PaymentProcessing processing;
                String method;
                if (choice.equals("1")) {
                    processing = new CreditCard();
                    method = "Credit Card";
                }else {
                    processing = new Cash();
                    method = "Cash";
                }
                System.out.print("Enter payment details: ");

                String details = scanner.nextLine();
                String result = processing.process(total, details);

                System.out.println(result);
                if (result.contains("Successful")){
                    Payment payment = new Payment( total, reference, "1" , method, "2025", "EGP");

                    payment.processPayment();
                    transaction.payments(payment);
                    booking.confirmBooking();
                    System.out.println("Booking confirmed");
                }
                break;
            }
        }
        if (!found) {
            System.out.println("Booking not found.");
        }
    }

    public void generateTicket() {
        System.out.println("Booking Reference:");
        String reference = scanner.nextLine();
        boolean found = false;

        for (Booking booking : bookings) {
            if (booking.getBookingReference().equals(reference)){
                found = true;
                ETicket ticket = new ETicket("10:00 AM", "Paris", booking.getFlightId(),"Cairo", booking.getCustomerId(), 1, "001" );
                ticket.printeticket();
                break;
            }
        }
        if (!found) {
            System.out.println("Booking not found.");
        }
    }

    public ArrayList<User> getUsers() {
        return users;
    }
    public FlightManager getFlightManager() {
        return flightManager;
    }
}
