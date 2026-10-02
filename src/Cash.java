public class Cash implements PaymentProcessing {
    @Override
    public String detailsofpayment(String details) {
        if (details == null) {
            return "Error has occurred please try again";
        } else {
            return "Details will be Printed";
        }
    }

    @Override
    public String process(double amounts, String details) {
        String validate = detailsofpayment(details);
        if (validate.equals("Details will be Printed")) {
            return "Payment of " + amounts + " is Successful";
        } else {
            return "Payment Failed";
        }
    }
}
