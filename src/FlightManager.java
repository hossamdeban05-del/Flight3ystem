import java.util.ArrayList;

public class FlightManager {

    private ArrayList<Flight> flights;

    public FlightManager() {

        flights = new ArrayList<>();
    }

    // Add Flight
    public void addFlight(Flight flight) {

        flights.add(flight);

        System.out.println("Flight Added Successfully.");
    }

    // Display Flights
    public void displayFlights() {

        for (Flight flight : flights) {

            System.out.println(flight);
            System.out.println("------------------");
        }
    }

    // Search Flights
    public ArrayList <Flight> searchFlights(String origin, String destination) {

        ArrayList<Flight> results = new ArrayList<>();

        boolean found = false;

        for (Flight flight : flights) {

            if (flight.getOrigin().equalsIgnoreCase(origin)
                    && flight.getDestination().equalsIgnoreCase(destination)) {

                results.add(flight);

                found = true;
            }
        }

        if (!found) {

            System.out.println("No Flights Found.");
        }
        return results;
    }

    // Getter
    public ArrayList<Flight> getFlights() {

        return flights;
    }
}