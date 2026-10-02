public class ETicket {
    protected String ticketid, name, flightid, fromdestination, destination, departingtime;
    protected int seatnumber;

    public ETicket(String departingtime, String destination, String flightid,
                   String fromdestination,
                   String name, int seatnumber, String ticketid) {
        this.departingtime = departingtime;
        this.destination = destination;
        this.flightid = flightid;
        this.fromdestination = fromdestination;
        this.name = name;
        this.seatnumber = seatnumber;
        this.ticketid = ticketid;
    }

    void printeticket() {
        System.out.println("++++++Ticket++++++++");
        System.out.println("Ticket ID: " + ticketid);
        System.out.println("Name: " + name);
        System.out.println("Seat Number: " + seatnumber);
        System.out.println("Flight ID: " + flightid);
        System.out.println("From: " + fromdestination);
        System.out.println("Destination: " + destination);
        System.out.println("Departure Time: " + departingtime);
        System.out.println("++++++++++++++++++++");

    }
}

