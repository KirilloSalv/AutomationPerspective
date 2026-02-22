package dto.customerData;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public record CustomerResponseObject(
        String referenceId,
        String citizenshipCountryCode,
        String firstName,
        String lastName,
        String dateOfBirth,
        String email,
        String phone,
        String locale,
        String routingGroup
)
{}
