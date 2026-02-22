package dto.PayoutCustomerData;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public record withdrawalResponse(
        String timestamp,
        String status,
        String error,
        String path,
        ResultResponsePayoutObject result
)
{}
