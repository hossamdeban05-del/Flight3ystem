import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class BookingFileManager {

    public void saveBookings(Booking booking) {
        try {
            FileWriter fw = new FileWriter("bookings.txt", true);
            PrintWriter pw = new PrintWriter(fw);

            pw.println(booking.getBookingReference() + "," + booking.getCustomerId() + "," + booking.getFlightId() + "," + booking.getStatus());
            pw.close();
        } catch (Exception e) {
            System.out.println("Error saving booking");
        }
    }

    public ArrayList<Booking> loadBookings() {
        ArrayList<Booking> bookingsList = new ArrayList<>();
        try {
            File file = new File("bookings.txt");

            if (!file.exists()) {
                return bookingsList;
            }

            Scanner scanner = new Scanner(file);
            // بنقرأ الملف سطر سطر
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");

                if (data.length == 4) {
                    Booking b = new Booking(data[0], data[1], data[2], "");
                    b.setStatus(data[3]);
                    bookingsList.add(b); // بنضيف الحجز المسترجع للقايمة
                }
            }
            scanner.close();
        } catch (Exception e) {
            System.out.println("Error loading bookings");
        }
        return bookingsList;
    }
}