import java.util.ArrayList;

public abstract class Flight {

    private String flightNumber;
    private String airline;
    private String origin;
    private String destination;
    private String departureTime;
    private String arrivalTime;

    protected double economyPrice;
    protected double businessPrice;
    protected double firstClassPrice;

    private ArrayList<Seat> seats;

    public Flight(String flightNumber, String airline,
                  String origin, String destination,
                  String departureTime, String arrivalTime,
                  double economyPrice,
                  double businessPrice,
                  double firstClassPrice) {

        this.flightNumber = flightNumber;
        this.airline = airline;
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;

        this.economyPrice = economyPrice;
        this.businessPrice = businessPrice;
        this.firstClassPrice = firstClassPrice;

        seats = new ArrayList<>();
    }

    // Abstract method (Polymorphism)
    public abstract double calculatePrice(String seatClass);

    public void addSeat(Seat seat) {
        seats.add(seat);
    }

    public boolean reserveSeat(String seatNumber) {

        for (Seat seat : seats) {

            if (seat.getSeatNumber().equalsIgnoreCase(seatNumber)
                    && !seat.isReserved()) {

                seat.reserveSeat();
                return true;
            }
        }

        return false;
    }

    public void checkAvailability() {

        for (Seat seat : seats) {
            System.out.println(seat);
        }
    }

    // Getters

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    @Override
    public String toString() {

        return "Flight Number: " + flightNumber +
                "\nAirline: " + airline +
                "\nRoute: " + origin + " -> " + destination +
                "\nDeparture: " + departureTime +
                "\nArrival: " + arrivalTime;
    }
}