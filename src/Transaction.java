import java.util.ArrayList;

public class Transaction {
    private ArrayList<String> recordings = new ArrayList<>();

    void payments(Payment payment) {
        String record = payment.getId() + " " + payment.getBookingreference() + " "
                + payment.getAmount() + " " + payment.getStatus();
        recordings.add(record);
        System.out.println("Transaction Saved");
    }
}
