package dto.PayoutCustomerData;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public record PaymentMethodDetails(
        String customerAccountNumber,
        String cardholderName,
        String cardExpiryMonth,
        String cardExpiryYear,
        String cardBrand,
        String cardIssuingCountry,
        String cardBank
)
{}
