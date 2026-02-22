package dto.API;

public class TokenPayment {
    public static final TokenAPI VALID_TOKEN = new TokenAPI(
            "vOREW5qSGxHZM4yiRg97aC0QqR57uUUQ"
    );

    public static final TokenAPI INVALID_TOKEN = new TokenAPI(
            "nYUsuXZgtpkswbftdsBnNqizwu2qMAW2"
    );

    public static TokenAPI valid() {
        return VALID_TOKEN;
    }

    public static TokenAPI invalid() {
        return INVALID_TOKEN;
    }
}
