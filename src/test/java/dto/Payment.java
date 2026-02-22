package dto;

import dto.customerData.*;

// {
//   "referenceId": "payment_id=45768;custom_ref=123",
//   "paymentType": "DEPOSIT",
//   "paymentMethod": "BASIC_CARD",
//   "amount": 11.12,
//   "currency": "EUR",
//   "description": "Funding the account number 12345",
//   "Card": {
//     "cardNumber": "4000 0000 0000 0002",
//     "cardholderName": "Harry Potter",
//     "cardSecurityCode": "130",
//     "expiryMonth": "07",
//     "expiryYear": "2028"
//   },
//   "customer": {
//     "referenceId": "VIP_customer_12345",
//     "citizenshipCountryCode": "GB",
//     "firstName": "Harry",
//     "lastName": "Potter",
//     "dateOfBirth": "1996-01-05",
//     "email": "customer@email.com",
//     "phone": "357 123456789",
//     "locale": "en",
//     "ip": "172.16.0.1"
//   },
//   "billingAddress": {
//     "addressLine1": "211, Victory street",
//     "addressLine2": "Office 7B",
//     "city": "Hogwarts",
//     "countryCode": "GB",
//     "postalCode": "01001",
//     "state": "CA"
//   },
//   "returnUrl": "https://yourwebsite.com/{id}/{referenceId}/{state}/{type}",
//   "pendingReturnUrl": "https://yourwebsite.com/{id}/{referenceId}/{state}/{type}",
//   "successReturnUrl": "https://yourwebsite.com/{id}/{referenceId}/{state}/{type}",
//   "declineReturnUrl": "https://yourwebsite.com/{id}/{referenceId}/{state}/{type}",
//   "webhookUrl": "https://yourwebsite.com/callbacks/payadmit",
//   "websiteUrl": "https://yourwebsite.com",
//   "additionalParameters": {
//     "countryOfBirth": "GB"
//   }
// }


import net.datafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public record Payment(
        String referenceId,
        String paymentType,
        String paymentMethod,
        String amount,
        String currency,
        String description,
        Card card,
        Customer customer,
        BillingAddress billingAddress
) { public static Payment apiCreatePayment() {

    Faker faker = new Faker();

    var customerFaker = faker.address();
    var customerFake = faker.name();

    LocalDate date = faker.timeAndDate().birthday();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    String dateOfBirth = date.format(formatter);

    var Card = CardSelector.get(CardType.VISA);

    String phone = "380 " + faker.number().digits(9);

    var BillingAddress = faker.address();


    Customer Customer = new Customer(
            faker.internet().uuid(),
            customerFaker.countryCode(),
            customerFake.firstName(),
            customerFake.lastName(),
            dateOfBirth,
            faker.internet().emailAddress(),
            phone,
            faker.locality().localeString(),
            faker.internet().ipV4Address(),
            faker.community().character()

    );

    BillingAddress Billing = new BillingAddress(
            BillingAddress.fullAddress(),
            BillingAddress.secondaryAddress(),
            BillingAddress.city(),
            BillingAddress.countryCode(),
            BillingAddress.postcode(),
            BillingAddress.state()

    );

return new Payment(
        faker.internet().uuid(),
        "DEPOSIT",
        "BASIC_CARD",
        "7",
        "EUR",
        "Payment test",
        Card,
        Customer,
        Billing

);

}

public Payment withoutPaymentType() {

        return new Payment(
                referenceId,
                null,
                paymentMethod,
                amount,
                currency,
                description,
                card,
                customer,
                billingAddress
        );

}


public Payment withoutCurrency() {

    return new Payment(
            referenceId,
            paymentType,
            paymentMethod,
            amount,
            null,
            description,
            card,
            customer,
            billingAddress

    );
}


public Payment emptyPayment() {

    return new Payment(
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
    );
}


public Payment negativeAmount() {

        return new Payment(
                referenceId,
                paymentType,
                paymentMethod,
                "-10",
                currency,
                description,
                card,
                customer,
                billingAddress
        );
}



public Payment withIncorrectNumber() {

        Faker faker = new Faker();

        String phoneIncorrect = "+380" + faker.number().digits(9);

        LocalDate date = faker.timeAndDate().birthday();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String dateOfBirth = date.format(formatter);

    Customer Customer = new Customer(
            faker.internet().uuid(),
            faker.address().countryCode(),
            faker.name().firstName(),
            faker.name().lastName(),
            dateOfBirth,
            faker.internet().emailAddress(),
            phoneIncorrect,
            faker.locality().localeString(),
            faker.internet().ipV4Address(),
            faker.community().character()

    );

        return new Payment(
                referenceId,
                paymentType,
                paymentMethod,
                amount,
                currency,
                description,
                card,
                Customer,
                billingAddress


        );
}





}
