public class Passenger {

    private String passengerId;
    private String Name;
    private String passportNumber;
    private String dateOfBirth;
    private String specialRequests;

    /// /Constructor
    public Passenger(String passengerId, String name, String passportNumber, String dateOfBirth, String specialRequests) {
        this.passengerId = passengerId;
        Name = name;
        this.passportNumber = passportNumber;
        this.dateOfBirth = dateOfBirth;
        this.specialRequests = specialRequests;
    }

    /// //////Setters and getters///

    public String getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(String passengerId) {
        this.passengerId = passengerId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getSpecialRequests() {
        return specialRequests;
    }

    public void setSpecialRequests(String specialRequests) {
        this.specialRequests = specialRequests;
    }

    // method to update passenger information
    public void updateInfo(String NewName, String NewPassport) {
        this.Name = NewName;
        this.passportNumber = NewPassport;
    }

// method to return passenger details

    public String getPassengerDetails() {
        return passengerId + " ___ " + Name + " ___ " + passportNumber;
    }

}