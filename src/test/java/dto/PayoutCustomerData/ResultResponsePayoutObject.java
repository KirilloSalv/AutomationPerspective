package dto.PayoutCustomerData;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public record ResultResponsePayoutObject(
        String id,
        String created,
        String paymentType,
        String state,
        String internalState,
        String description,
        String paymentMethod,
        PaymentMethodDetails paymentMethodDetails,
        int amount,
        String currency,
        String websiteUrl,
        int customerAmount,
        String customerCurrency,
        String externalResultCode,
        ExternalRefs externalRefs,
        CustomerResponsePayoutObject customer,
        BillingAddressResponsePayoutObject billingAddress,
        String terminalName
)
{}
