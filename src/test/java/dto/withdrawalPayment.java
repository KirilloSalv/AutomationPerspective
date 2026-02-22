package dto;

import dto.PayoutCustomerData.Receiver;
import dto.customerData.*;
import net.datafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public record withdrawalPayment(
        String referenceId,
        String paymentType,
        String paymentMethod,
        String amount,
        String currency,
        String description,
        Card card,
        Customer customer,
        BillingAddress billingAddress,
        Receiver receiver
) { public static withdrawalPayment apiCreatePayout() {

        Faker faker = new Faker();

    LocalDate date = faker.timeAndDate().birthday();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    String dateOfBirth = date.format(formatter);

    var Card = CardSelector.get(CardType.VISA);

    String phone = "380 " + faker.number().digits(9);

    Customer customerPayout = new Customer(
            faker.internet().uuid(),
            faker.address().countryCode(),
            faker.name().firstName(),
            faker.name().lastName(),
            dateOfBirth,
            faker.internet().emailAddress(),
            phone,
            faker.locality().localeString(),
            faker.internet().ipV4Address(),
            faker.community().character()
    );

    var BillingAddress = faker.address();

    BillingAddress billingPayout = new BillingAddress(
            BillingAddress.fullAddress(),
            BillingAddress.secondaryAddress(),
            BillingAddress.city(),
            BillingAddress.countryCode(),
            BillingAddress.postcode(),
            BillingAddress.state()
    );

    String IBANPayout = "DE" + faker.number().digits(29);

    String bankCode = "HB" + faker.number().digits(9);

        Receiver receiverPayout = new Receiver(
                faker.name().firstName(),
                faker.name().lastName(),
                IBANPayout,
                bankCode,
                faker.expression("Bank of Baroda"),
                faker.number().digits(6),
                faker.azure().storageAccount()
        );



    return new withdrawalPayment(
            faker.internet().uuid(),
            "WITHDRAWAL",
            "BASIC_CARD",
            "7.49",
            "EUR",
            faker.text().text(),
            Card,
            customerPayout,
            billingPayout,
            receiverPayout

    );
}


public withdrawalPayment withNegativeAmount() {

    return new withdrawalPayment(
            referenceId,
            paymentType,
            paymentMethod,
            "-10",
            currency,
            description,
            card,
            customer,
            billingAddress,
            receiver
    );
}


public withdrawalPayment withoutCard() {

    return new withdrawalPayment(
            referenceId,
            paymentType,
            paymentMethod,
            amount,
            currency,
            description,
            null,
            customer,
            billingAddress,
            receiver
    );
}


public  withdrawalPayment withZeroAmount() {

    return new withdrawalPayment(
            referenceId,
            paymentType,
            paymentMethod,
            "0",
            currency,
            description,
            card,
            customer,
            billingAddress,
            receiver
    );
}

}
