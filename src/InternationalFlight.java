public class InternationalFlight extends Flight {

    private double internationalFees = 1500;

    public InternationalFlight(String flightNumber, String airline,
                               String origin, String destination,
                               String departureTime, String arrivalTime,
                               double economyPrice,
                               double businessPrice,
                               double firstClassPrice) {

        super(flightNumber, airline, origin, destination,
                departureTime, arrivalTime,
                economyPrice, businessPrice, firstClassPrice);
    }

    @Override
    public double calculatePrice(String seatClass) {

        switch (seatClass.toLowerCase()) {

            case "economy":
                return economyPrice + internationalFees;

            case "business":
                return businessPrice + internationalFees;

            case "first":
                return firstClassPrice + internationalFees;

            default:
                return 0;
        }
    }
}