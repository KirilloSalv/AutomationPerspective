package dto.customerData;

public record Customer(
        String referenceId,
        String citizenshipCountryCode,
        String firstName,
        String lastName,
        String dateOfBirth,
        String email,
        String phone,
        String locale,
        String ip,
        String routingGroup
)
{}
