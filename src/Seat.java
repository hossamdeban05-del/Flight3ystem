public class Seat {

    private String seatNumber;
    private String seatClass; // Economy, Business, First
    private boolean reserved;

    public Seat(String seatNumber, String seatClass) {
        this.seatNumber = seatNumber;
        this.seatClass = seatClass;
        this.reserved = false;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public String getSeatClass() {
        return seatClass;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void reserveSeat() {
        reserved = true;
    }

    public void cancelReservation() {
        reserved = false;
    }

    @Override
    public String toString() {
        return seatNumber + " - " + seatClass + " - " +
                (reserved ? "Reserved" : "Available");
    }
}