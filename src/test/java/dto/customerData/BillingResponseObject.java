package dto.customerData;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public record BillingResponseObject(
            String countryCode,
            String addressLine1,
            String addressLine2,
            String city,
            String state,
            String postalCode
)
{}
