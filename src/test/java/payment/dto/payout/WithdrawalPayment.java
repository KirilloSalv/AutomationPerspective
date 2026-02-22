package payment.dto.payout;

import dto.PayoutCustomerData.Receiver;
import dto.customerData.BillingAddress;
import dto.customerData.CardSelector;
import dto.customerData.CardType;
import dto.customerData.Customer;
import net.datafaker.Faker;
import payment.dto.Card;
import payment.dto.PaymentMethod;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static payment.dto.PaymentMethod.BASIC_CARD;

public record WithdrawalPayment(
        String referenceId,
        String paymentType,
        PaymentMethod paymentMethod,
        Double amount,
        String currency,
        String description,
        Card card,
        Customer customer,
        BillingAddress billingAddress,
        Receiver receiver
) {
    public static WithdrawalPayment create() {
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


        return new WithdrawalPayment(
                faker.internet().uuid(),
                "WITHDRAWAL",
                BASIC_CARD,
                7.49,
                "EUR",
                faker.text().text(),
                Card,
                customerPayout,
                billingPayout,
                receiverPayout

        );
    }


    public WithdrawalPayment withNegativeAmount() {
        return new WithdrawalPayment(
                referenceId,
                paymentType,
                paymentMethod,
                -amount,
                currency,
                description,
                card,
                customer,
                billingAddress,
                receiver
        );
    }


    public WithdrawalPayment withoutCard() {
        return new WithdrawalPayment(
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


    public WithdrawalPayment withZeroAmount() {
        return new WithdrawalPayment(
                referenceId,
                paymentType,
                paymentMethod,
                0.0,
                currency,
                description,
                card,
                customer,
                billingAddress,
                receiver
        );
    }

}
