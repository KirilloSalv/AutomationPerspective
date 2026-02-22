package dto.customerData;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public record ResultResponseObject(
        String id,
        String created,
        String paymentType,
        String state,
        String internalState,
        String description,
        String paymentMethod,
        int amount,
        String currency,
        String websiteUrl,
        int customerAmount,
        String customerCurrency,
        String redirectUrl,
        String externalResultCode,
        CustomerResponseObject customer,
        BillingResponseObject billingAddress,
        String terminalName
)
{}
