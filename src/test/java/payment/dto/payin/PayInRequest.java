package payment.dto.payin;

import dto.customerData.BillingAddress;
import dto.customerData.CardSelector;
import dto.customerData.CardType;
import dto.customerData.Customer;
import net.datafaker.Faker;
import net.datafaker.providers.base.Address;
import net.datafaker.providers.base.Name;
import payment.dto.Card;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public record PayInRequest(
        String referenceId,
        String paymentType,
        String paymentMethod,
        Integer amount,
        String currency,
        String description,
        Card card,
        Customer customer,
        BillingAddress billingAddress
) {
    public static PayInRequest create() {

        Faker faker = new Faker();
        Address fakeCustomerAddress = faker.address();
        Name fakeCustomerName = faker.name();
        Address fakeBillingAddress = faker.address();
        String fakePhoneNumber = "380 " + faker.number().digits(9);

        LocalDate date = faker.timeAndDate().birthday();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fakeDateOfBirth = date.format(formatter);

        Card Card = CardSelector.get(CardType.VISA);

        Customer Customer = new Customer(
                faker.internet().uuid(),
                fakeCustomerAddress.countryCode(),
                fakeCustomerName.firstName(),
                fakeCustomerName.lastName(),
                fakeDateOfBirth,
                faker.internet().emailAddress(),
                fakePhoneNumber,
                faker.locality().localeString(),
                faker.internet().ipV4Address(),
                faker.community().character()

        );

        BillingAddress Billing = new BillingAddress(
                fakeBillingAddress.fullAddress(),
                fakeBillingAddress.secondaryAddress(),
                fakeBillingAddress.city(),
                fakeBillingAddress.countryCode(),
                fakeBillingAddress.postcode(),
                fakeBillingAddress.state()

        );

        return new PayInRequest(
                faker.internet().uuid(),
                "DEPOSIT",
                "BASIC_CARD",
                7,
                "EUR",
                "Payment test",
                Card,
                Customer,
                Billing

        );

    }

    public PayInRequest withoutPaymentType() {
        return new PayInRequest(
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


    public PayInRequest withoutCurrency() {
        return new PayInRequest(
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


    public PayInRequest emptyPayment() {
        return new PayInRequest(
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


    public PayInRequest negativeAmount() {
        return new PayInRequest(
                referenceId,
                paymentType,
                paymentMethod,
                -amount,
                currency,
                description,
                card,
                customer,
                billingAddress
        );
    }

    public PayInRequest maestro() {
        return new PayInRequest(
                referenceId,
                paymentType,
                paymentMethod,
                -amount,
                currency,
                description,
                CardSelector.get(CardType.MAESTRO),
                customer,
                billingAddress
        );
    }


    public PayInRequest withIncorrectNumber() {

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

        return new PayInRequest(
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
