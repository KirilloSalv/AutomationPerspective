package API;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.RequestOptions;
import dto.API.TokenPayment;
import dto.Payment;
import dto.PayoutCustomerData.withdrawalResponse;
import dto.customerData.JSONMapper;
import dto.customerData.PaymentResponse;
import dto.withdrawalPayment;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


@UsePlaywright
public class СreatePayment {

    private APIRequestContext request;



        @BeforeEach
        void beforeAPIRequest (Playwright playwright) {

            String token = TokenPayment.valid().accessToken();

            request = playwright.request().newContext(
                    new APIRequest.NewContextOptions()
                            .setBaseURL("https://app-demo.payadmit.com")
                            .setExtraHTTPHeaders(Map.of(
                                    "Authorization", "Bearer " + token,
                                    "Content-Type", "application/json"
                            ))

            );

        }

        @AfterEach
        void afterAPIRequest () {
            if (request != null) {
                request.dispose();
            }
        }


        @DisplayName("Payin: Create Payment")
        @Test
        void createAPIPayment () throws JsonProcessingException {

            Payment createPayment = Payment.apiCreatePayment();

            ObjectMapper mapper = new ObjectMapper();

            String jsonBody = mapper.writeValueAsString(createPayment);


            var APIresponse = request.post("/api/v1/payments",
                    RequestOptions.create()
                            .setData(jsonBody)
                    );

            System.out.println("Request 1:JSON ");
            System.out.println(
                    mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(createPayment)
            );

            assertThat(APIresponse.status()).isEqualTo(200);

            PaymentResponse paymentResponse = JSONMapper.fromJson(APIresponse.text(),PaymentResponse.class);

            System.out.println(JSONMapper.toPrettyJson(paymentResponse));

            assertThat(paymentResponse.result().redirectUrl()).isNotBlank();





        }


        @DisplayName("Payin: Payment without 'paymentType' param")
        @Test

        void paymentWithoutType() throws JsonProcessingException {

            ObjectMapper mapper= new ObjectMapper();

            Payment paymentNoType = Payment.apiCreatePayment()
                    .withoutPaymentType();

            String jsonBody = mapper.writeValueAsString(paymentNoType);

            var ResponseWithoutType = request.post("/api/v1/payments",
                    RequestOptions.create()
                            .setData(jsonBody)
            
            );

            System.out.println("Request 2: JSON");
            System.out.println(
                    mapper.writerWithDefaultPrettyPrinter()
                            .writeValueAsString(paymentNoType)
            );

            assertThat(ResponseWithoutType.status()).isEqualTo(400);

            PaymentResponse responseNoType = JSONMapper.fromJson(ResponseWithoutType.text(),PaymentResponse.class);

            System.out.println(JSONMapper.toPrettyJson(responseNoType));

        }


        @DisplayName("Payin: Payment without 'currency' param")
        @Test

        void paymentWithoutCurrency() throws JsonProcessingException {

            ObjectMapper mapper = new ObjectMapper();

            Payment paymentNoCurrency = Payment.apiCreatePayment()
                    .withoutCurrency();

            String jsonBody = mapper.writeValueAsString(paymentNoCurrency);

            var ResponseWithoutType = request.post("/api/v1/payments",
                    RequestOptions.create()
                            .setData(jsonBody));

        assertThat(ResponseWithoutType.status()).isEqualTo(400);

        System.out.println("Request 3: JSON");
        System.out.println(mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(paymentNoCurrency));

        PaymentResponse responseNoCurrency = JSONMapper.fromJson(ResponseWithoutType.text(),PaymentResponse.class);

        System.out.println(JSONMapper.toPrettyJson(responseNoCurrency));


        }





        @DisplayName("Payin: Empty Payment")
        @Test

        void EmptyPayment() throws JsonProcessingException {

            ObjectMapper mapper = new ObjectMapper();

            Payment emptyPayment = Payment.apiCreatePayment()
                    .emptyPayment();

            String jsonBody = mapper.writeValueAsString(emptyPayment);

            var EmptyResponse = request.post("/api/v1/payments",
                    RequestOptions.create()
                            .setData(jsonBody));

            System.out.println("Request 4: JSON");
            System.out.println(
                    mapper.writerWithDefaultPrettyPrinter()
                            .writeValueAsString(emptyPayment)
            );

            assertThat(EmptyResponse.status()).isEqualTo(400);

            PaymentResponse emptyResponse = JSONMapper.fromJson(EmptyResponse.text(),PaymentResponse.class);

            System.out.println(JSONMapper.toPrettyJson(emptyResponse));

        }



        @DisplayName("Payin: Payment with negative amount")
        @Test
        void withNegativeAmount() throws JsonProcessingException {

            ObjectMapper mapper = new ObjectMapper();

            Payment negativeAmount = Payment.apiCreatePayment()
                    .negativeAmount();

            String jsonBody = mapper.writeValueAsString(negativeAmount);

            var PaymentWithNegativeAmount = request.post("/api/v1/payments",
                    RequestOptions.create()
                            .setData(jsonBody));

            System.out.println("Request 5: JSON");
            System.out.println(
                    mapper.writerWithDefaultPrettyPrinter()
                            .writeValueAsString(negativeAmount)
            );

            assertThat(PaymentWithNegativeAmount.status()).isEqualTo(400);

            PaymentResponse responseWithNegativeAmount = JSONMapper.fromJson(PaymentWithNegativeAmount.text(),PaymentResponse.class);

            System.out.println(JSONMapper.toPrettyJson(responseWithNegativeAmount));
        }




        @DisplayName("Payin: Payment with Incorrect phone number")
        @Test
        void paymentWithIncorrectNumber() throws JsonProcessingException {

            ObjectMapper mapper = new ObjectMapper();

            Payment withIncorrectPhone = Payment.apiCreatePayment()
                    .withIncorrectNumber();

            String jsonBody = mapper.writeValueAsString(withIncorrectPhone);

            var phoneIncorrect = request.post("api/v1/payments",
                    RequestOptions.create()
                            .setData(jsonBody));

            System.out.println("Request 6:JSON");
            System.out.println(
                    mapper.writerWithDefaultPrettyPrinter()
                            .writeValueAsString(withIncorrectPhone)
            );

            assertThat(phoneIncorrect.status()).isEqualTo(400);

            PaymentResponse responseWithIncorrectPhone = JSONMapper.fromJson(phoneIncorrect.text(),PaymentResponse.class);

            System.out.println(JSONMapper.toPrettyJson(responseWithIncorrectPhone));


        }


        @DisplayName("Payin: Payment with invalid token")
        @Test
        void paymentWithInvalidToken() throws JsonProcessingException {

            ObjectMapper mapper = new ObjectMapper();

            String token = TokenPayment.INVALID_TOKEN.accessToken();

            Payment withInvalidToken = Payment.apiCreatePayment();

            String jsonBody = mapper.writeValueAsString(withInvalidToken);

            var APIWithInvalidToken = request.post("api/v1/payments",
                    RequestOptions.create()
                            .setData(jsonBody)
                            .setHeader("Authorization", "Bearer " + token));

            System.out.println("Request 6: JSON");
            System.out.println(
                    mapper.writerWithDefaultPrettyPrinter()
                            .writeValueAsString(withInvalidToken)
            );

            assertThat(APIWithInvalidToken.status()).isEqualTo(401);

            PaymentResponse ResponseWithInvalidToken = JSONMapper.fromJson(APIWithInvalidToken.text(), PaymentResponse.class);

            System.out.println(JSONMapper.toPrettyJson(ResponseWithInvalidToken));


        }



        @DisplayName("Payout: Create Payment")
        @Test
        void CreateWithdrawal() throws JsonProcessingException {

            ObjectMapper mapper = new ObjectMapper();

            withdrawalPayment CreatePayout = withdrawalPayment.apiCreatePayout();

            String jsonBody = mapper.writeValueAsString(CreatePayout);

            var ApiPayout = request.post("/api/v1/payments",
                    RequestOptions.create()
                            .setData(jsonBody));

            System.out.println("Request Payout 1: JSON");
            System.out.println(
                    mapper.writerWithDefaultPrettyPrinter()
                            .writeValueAsString(CreatePayout)
            );

            assertThat(ApiPayout.status()).isEqualTo(200);

            withdrawalResponse PayoutResponse = JSONMapper.fromJson(ApiPayout.text(),withdrawalResponse.class);

            System.out.println(JSONMapper.toPrettyJson(PayoutResponse));



        }


        @DisplayName("Payout: With negative amount")
        @Test
        void WithNegativeAmount() throws JsonProcessingException {

            ObjectMapper mapper = new ObjectMapper();

            withdrawalPayment withNegativeAmount = withdrawalPayment.apiCreatePayout()
                    .withNegativeAmount();

            String jsonBody = mapper.writeValueAsString(withNegativeAmount);

            var WithdrawalWithNegativeAmount = request.post("/api/v1/payments",
                    RequestOptions.create()
                            .setData(jsonBody));

            System.out.println("Request Payout 2: JSON");
            System.out.println(
                    mapper.writerWithDefaultPrettyPrinter()
                            .writeValueAsString(withNegativeAmount)
            );

            assertThat(WithdrawalWithNegativeAmount.status()).isEqualTo(400);

            withdrawalResponse NegativeAmountResponse = JSONMapper.fromJson(WithdrawalWithNegativeAmount.text(),withdrawalResponse.class);

            System.out.println(JSONMapper.toPrettyJson(NegativeAmountResponse));
        }




        @DisplayName("Payout: With no card in request")
        @Test
        void WithoutCard() throws JsonProcessingException {

            ObjectMapper mapper = new ObjectMapper();

            withdrawalPayment withoutCard = withdrawalPayment.apiCreatePayout()
                    .withoutCard();

            String jsonBody = mapper.writeValueAsString(withoutCard);

            var APIWithoutCard = request.post("/api/v1/payments",
                    RequestOptions.create()
                            .setData(jsonBody));


            System.out.println("Request Payout 3: JSON");
            System.out.println(
                    mapper.writerWithDefaultPrettyPrinter()
                            .writeValueAsString(withoutCard)
            );

            assertThat(APIWithoutCard.status()).isEqualTo(400);

            withdrawalResponse ResponseWithoutCard = JSONMapper.fromJson(APIWithoutCard.text(), withdrawalResponse.class);

            System.out.println(JSONMapper.toPrettyJson(ResponseWithoutCard));

        }




        @DisplayName("Payout: With Zero amount")
        @Test
        void WithZeroAmount() throws JsonProcessingException {

            ObjectMapper mapper = new ObjectMapper();

            withdrawalPayment withZeroAmount = withdrawalPayment.apiCreatePayout()
                    .withZeroAmount();

            String jsonBody = mapper.writeValueAsString(withZeroAmount);

            var APIZeroAmount = request.post("/api/v1/payments",
                    RequestOptions.create()
                            .setData(jsonBody));


            System.out.println("Request Payout 4: JSON");
            System.out.println(
                    mapper.writerWithDefaultPrettyPrinter()
                                    .writeValueAsString(withZeroAmount));


            assertThat(APIZeroAmount.status()).isEqualTo(400);

            withdrawalResponse ResponseWithZeroAmount = JSONMapper.fromJson(APIZeroAmount.text(), withdrawalResponse.class);

            System.out.println(JSONMapper.toPrettyJson(ResponseWithZeroAmount));


        }


    }