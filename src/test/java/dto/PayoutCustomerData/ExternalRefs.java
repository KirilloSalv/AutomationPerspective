package dto.PayoutCustomerData;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public record ExternalRefs(
        String withdrawTransactionGuid,
        String withdrawTransactionId
)
{}
