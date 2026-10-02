import java.util.ArrayList;

public class Booking {
    private String bookingReference;

    private String customerId; //مؤقت
    private String flightId; //مؤقت
    private ArrayList<Passenger> passengers;//array for passengers
    private String seatSelections;
    private String status;
    private String paymentStatus;


    /// constructor
    public Booking(String bookingReference, String customerId, String flightId, String seatSelections) {
        this.bookingReference = bookingReference;
        this.customerId = customerId;
        this.flightId = flightId;
        this.passengers = new ArrayList<>();
        this.seatSelections = seatSelections;
        this.status = "Pending";
        this.paymentStatus = "Pending";
    }


    /// setters and getters//

    public String getBookingReference() {
        return bookingReference;
    }

    public void setBookingReference(String bookingReference) {
        this.bookingReference = bookingReference;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public ArrayList<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(ArrayList<Passenger> passengers) {
        this.passengers = passengers;
    }

    public String getSeatSelections() {
        return seatSelections;
    }

    public void setSeatSelections(String seatSelections) {
        this.seatSelections = seatSelections;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    /// ///////////////////////////////
//  method to add passenger
    public void addPassenger(Passenger passenger) {
        passengers.add(passenger);
    }

    // this method for calc total price
    public double calculateTotalPrice(double flightPrice) {
        return passengers.size() * flightPrice;
    }

    //to confirm booking
    public void confirmBooking() {
        this.status = "Confirmed";
        this.paymentStatus = "Paid";
    }

    //to cancel booking
    public void cancelBooking() {
        this.status = "Cancelled ";
    }

    //خط سير الرحلة
    public String generateItinerary() {
        String itinerary = "Booking Ref: " + bookingReference + "\n";
        itinerary += "Flight ID: " + flightId + "\n";
        itinerary += "Status: " + status + "\n";
        itinerary += "Passengers:\n";

        // this loop to add passenger details
        for (int i = 0; i < passengers.size(); i++) {
            itinerary += passengers.get(i).getPassengerDetails() + "\n";
        }
        return itinerary;

    }
}