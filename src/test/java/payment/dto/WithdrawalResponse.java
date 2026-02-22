package payment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dto.PayoutCustomerData.ResultResponsePayoutObject;

@JsonIgnoreProperties(ignoreUnknown = true)

public record WithdrawalResponse(
        String timestamp,
        String status,
        String error,
        String path,
        ResultResponsePayoutObject result
)
{}
