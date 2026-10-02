import java.io.*;
import java.util.ArrayList;

public class FlightFileManager {

    public static void saveFlights(ArrayList<Flight> flights) {

        try {

            PrintWriter writer =
                    new PrintWriter(new FileWriter("flights.txt"));

            for (Flight flight : flights) {

                writer.println(
                        flight.getFlightNumber() + "|" +
                                flight.getOrigin() + "|" +
                                flight.getDestination() + "|" +
                                flight.getDepartureTime()
                );
            }

            writer.close();

            System.out.println("Flights Saved Successfully.");

        } catch (IOException e) {

            System.out.println("Error saving flights.");
        }
    }
}