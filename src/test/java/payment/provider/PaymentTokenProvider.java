package payment.provider;

public class PaymentTokenProvider {
    private static final String VALID_TOKEN = "vOREW5qSGxHZM4yiRg97aC0QqR57uUUQ";
    private static final String INVALID_TOKEN = "nYUsuXZgtpkswbftdsBnNqizwu2qMAW2";

    public static String valid() {
        return VALID_TOKEN;
    }

    public static String invalid() {
        return INVALID_TOKEN;
    }
}
