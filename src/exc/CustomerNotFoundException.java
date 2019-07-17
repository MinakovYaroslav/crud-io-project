package exc;

public class CustomerNotFoundException extends Throwable {

    private String customerId;

    public CustomerNotFoundException(String customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "Customer with id " + customerId + "not found";
    }
}
