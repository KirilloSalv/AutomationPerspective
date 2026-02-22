package dto.PayoutCustomerData;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public record CustomerResponsePayoutObject(
        String referenceId,
        String citizenshipCountryCode,
        String firstName,
        String lastName,
        String dateOfBirth,
        String email,
        String phone,
        String locale,
        String routingGroup,
        String iban
)
{}
