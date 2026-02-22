package dto.PayoutCustomerData;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public record BillingAddressResponsePayoutObject(
        String countryCode,
        String addressLine1,
        String addressLine2,
        String city,
        String state,
        String postalCode
) {
}
