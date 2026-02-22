package dto.PayoutCustomerData;

public record Receiver(
        String firstName,
        String lastName,
        String iban,
        String bankCode,
        String bankName,
        String sortCode,
        String accountNumber
)
{}
