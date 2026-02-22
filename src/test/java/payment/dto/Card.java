package payment.dto;

public record Card(
        String cardNumber,
        String cardholderName,
        String cardSecurityCode,
        String expiryMonth,
        String expiryYear)
{}

