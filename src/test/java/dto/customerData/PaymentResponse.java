package dto.customerData;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PaymentResponse(
        String timestamp,
        String status,
        String error,
        String path,
        ResultResponseObject result
)
{}

//{
//        "timestamp": "2026-02-15T17:49:14.365+00:00",
//        "status": 200,
//        "result": {
//        "id": "e358c2ba6d214f1ebadc33940ba7a9f4",
//        "created": "2026-02-15T17:49:14.333558401",
//        "paymentType": "DEPOSIT",
//        "state": "CHECKOUT",
//        "internalState": "CHECKOUT",
//        "description": "Payout for the account number 12345",
//        "paymentMethod": "BASIC_CARD",
//        "amount": 1,
//        "currency": "EUR",
//        "websiteUrl": "https://www.google.com",
//        "customerAmount": 1,
//        "customerCurrency": "EUR",
//        "redirectUrl": "https://app-demo.zinzipay.com/payment/e358c2ba6d214f1ebadc33940ba7a9f4",
//        "externalResultCode": null,
//        "customer": {
//        "referenceId": "VIP_customer_12345",
//        "citizenshipCountryCode": "GB",
//        "firstName": "Harry",
//        "lastName": "Potter",
//        "dateOfBirth": "1996-01-05",
//        "email": "customer@email.com",
//        "phone": "357 32341432423",
//        "locale": "nl_BE",
//        "routingGroup": "VIP_Campaign"
//        },
//        "billingAddress": {
//        "countryCode": "GB",
//        "addressLine1": "211, Victory,Main street",
//        "addressLine2": "Office 7B",
//        "city": "Hogwarts",
//        "state": "CA",
//        "postalCode": "01001"
//        },
//        "terminalName": null
//        }
//        }