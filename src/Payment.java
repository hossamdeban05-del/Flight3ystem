public class Payment {
    protected String id, bookingreference, method, status, transactiondate, currency;
    protected double amount;

    public Payment(double amount, String bookingreference, String id, String method, String transactiondate, String currency) {
        this.amount = amount;
        this.bookingreference = bookingreference;
        this.id = id;
        this.method = method;
        this.transactiondate = transactiondate;
        this.currency = currency;
    }

    public void updateStatus(String newStatus) {
        this.status = newStatus;
        System.out.println("Status updated: " + status);
    }

    public void processPayment() {
        System.out.println("Processing payment: " + id);
        updateStatus("Done!");
    }

    public void validatePaymentDetails() {
        if (amount > 0) {
            System.out.println("Correct Details");
        } else {
            System.out.println("Invalid Details");
        }
    }

    public double getAmount() {
        return amount;
    }

    public String getBookingreference() {
        return bookingreference;
    }

    public String getCurrency() {
        return currency;
    }

    public String getId() {
        return id;
    }

    public String getMethod() {
        return method;
    }

    public String getStatus() {
        return status;
    }

    public String getTransactiondate() {
        return transactiondate;
    }
}
