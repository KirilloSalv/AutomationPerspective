package dto.customerData;

public record BillingAddress(
        String addressLine1,
        String addressLine2,
        String city,
        String countryCode,
        String postalCode,
        String state
) {
}
