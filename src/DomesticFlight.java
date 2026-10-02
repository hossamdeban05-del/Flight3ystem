public class DomesticFlight extends Flight {

    public DomesticFlight(String flightNumber, String airline,
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
                return economyPrice;

            case "business class":
                return businessPrice;

            case "first class":
                return firstClassPrice;

            default:
                return 0;
        }
    }
}