package payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.RequestOptions;
import dto.customerData.JSONMapper;
import dto.customerData.PaymentResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import payment.dto.WithdrawalResponse;
import payment.dto.payin.PayInRequest;
import payment.dto.payout.WithdrawalPayment;
import payment.provider.PaymentTokenProvider;

import java.util.Map;

import static java.net.HttpURLConnection.*;
import static org.assertj.core.api.Assertions.assertThat;


@UsePlaywright
public class CreatePaymentTest {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(CreatePaymentTest.class);

    private APIRequestContext request;

    @BeforeEach
    void beforeAPIRequest(Playwright playwright) {
        String token = PaymentTokenProvider.valid();
        APIRequest.NewContextOptions requestContext = new APIRequest.NewContextOptions()
                .setBaseURL("https://app-demo.payadmit.com")
                .setExtraHTTPHeaders(Map.of(
                        "Authorization", "Bearer " + token,
                        "Content-Type", "application/json")
                );
        request = playwright.request().newContext(requestContext);
    }

    @AfterEach
    void afterAPIRequest() {
        if (request != null) {
            request.dispose();
        }
    }


    @DisplayName("Payin: Create Payment")
    @Test
    void shouldCreatePaymentSuccessfully() throws JsonProcessingException {
        PayInRequest createPayment = PayInRequest.create();
        String jsonBody = mapper.writeValueAsString(createPayment);

        APIResponse APIresponse = request.post("/api/v1/payments", RequestOptions.create().setData(jsonBody));

        logger.info("Request 1: JSON ");
        logger.info(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(createPayment));

        assertThat(APIresponse.status()).isEqualTo(HTTP_OK);

        PaymentResponse paymentResponse = JSONMapper.fromJson(APIresponse.text(), PaymentResponse.class);

        logger.info(JSONMapper.toPrettyJson(paymentResponse));

        assertThat(paymentResponse.result().redirectUrl()).isNotBlank();
    }


    @DisplayName("Payin: Payment without 'paymentType' param")
    @Test
    void shouldFailPaymentCreationWithoutPaymentType() throws JsonProcessingException {
        PayInRequest paymentNoType = PayInRequest.create().withoutPaymentType();

        String jsonBody = mapper.writeValueAsString(paymentNoType);

        APIResponse ResponseWithoutType = request.post("/api/v1/payments", RequestOptions.create().setData(jsonBody));

        logger.info("Request 2: JSON");
        logger.info(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(paymentNoType));

        assertThat(ResponseWithoutType.status()).isEqualTo(HTTP_BAD_REQUEST);

        PaymentResponse responseNoType = JSONMapper.fromJson(ResponseWithoutType.text(), PaymentResponse.class);

        logger.info(JSONMapper.toPrettyJson(responseNoType));
    }


    @DisplayName("Payin: Payment without 'currency' param")
    @Test
    void shouldFailPaymentCreationWithoutCurrency() throws JsonProcessingException {

        PayInRequest paymentNoCurrency = PayInRequest.create().withoutCurrency();

        String jsonBody = mapper.writeValueAsString(paymentNoCurrency);

        var ResponseWithoutType = request.post("/api/v1/payments", RequestOptions.create().setData(jsonBody));

        assertThat(ResponseWithoutType.status()).isEqualTo(HTTP_BAD_REQUEST);

        logger.info("Request 3: JSON");
        logger.info(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(paymentNoCurrency));

        PaymentResponse responseNoCurrency = JSONMapper.fromJson(ResponseWithoutType.text(), PaymentResponse.class);

        logger.info(JSONMapper.toPrettyJson(responseNoCurrency));
    }


    @DisplayName("Payin: Empty Payment")
    @Test
    void EmptyPayment() throws JsonProcessingException {
        PayInRequest emptyPayment = PayInRequest.create().emptyPayment();
        String jsonBody = mapper.writeValueAsString(emptyPayment);
        var EmptyResponse = request.post("/api/v1/payments", RequestOptions.create().setData(jsonBody));
        logger.info("Request 4: JSON");
        logger.info(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(emptyPayment));

        assertThat(EmptyResponse.status()).isEqualTo(HTTP_BAD_REQUEST);

        PaymentResponse emptyResponse = JSONMapper.fromJson(EmptyResponse.text(), PaymentResponse.class);

        logger.info(JSONMapper.toPrettyJson(emptyResponse));

    }


    @DisplayName("Payin: Payment with negative amount")
    @Test
    void withNegativeAmount() throws JsonProcessingException {

        PayInRequest negativeAmount = PayInRequest.create().negativeAmount();

        String jsonBody = mapper.writeValueAsString(negativeAmount);

        var paymentWithNegativeAmount = request.post("/api/v1/payments", RequestOptions.create().setData(jsonBody));

        logger.info("Request 5: JSON");
        logger.info(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(negativeAmount));

        assertThat(paymentWithNegativeAmount.status()).isEqualTo(HTTP_BAD_REQUEST);

        PaymentResponse responseWithNegativeAmount = JSONMapper.fromJson(paymentWithNegativeAmount.text(), PaymentResponse.class);

        logger.info(JSONMapper.toPrettyJson(responseWithNegativeAmount));
    }


    @DisplayName("Payin: Payment with Incorrect phone number")
    @Test
    void paymentWithIncorrectNumber() throws JsonProcessingException {

        PayInRequest withIncorrectPhone = PayInRequest.create().withIncorrectNumber();

        String jsonBody = mapper.writeValueAsString(withIncorrectPhone);

        var phoneIncorrect = request.post("api/v1/payments", RequestOptions.create().setData(jsonBody));

        logger.info("Request 6:JSON");
        logger.info(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(withIncorrectPhone));

        assertThat(phoneIncorrect.status()).isEqualTo(HTTP_BAD_REQUEST);

        PaymentResponse responseWithIncorrectPhone = JSONMapper.fromJson(phoneIncorrect.text(), PaymentResponse.class);

        logger.info(JSONMapper.toPrettyJson(responseWithIncorrectPhone));


    }


    @DisplayName("Payin: Payment with invalid token")
    @Test
    void paymentWithInvalidToken() throws JsonProcessingException {

        String token = PaymentTokenProvider.invalid();
        PayInRequest withInvalidToken = PayInRequest.create();
        String jsonBody = mapper.writeValueAsString(withInvalidToken);

        var aPIWithInvalidToken = request.post("api/v1/payments", RequestOptions.create().setData(jsonBody).setHeader("Authorization", "Bearer " + token));

        logger.info("Request 6: JSON");
        logger.info(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(withInvalidToken));
        assertThat(aPIWithInvalidToken.status()).isEqualTo(HTTP_UNAUTHORIZED);
        PaymentResponse ResponseWithInvalidToken = JSONMapper.fromJson(aPIWithInvalidToken.text(), PaymentResponse.class);
        logger.info(JSONMapper.toPrettyJson(ResponseWithInvalidToken));


    }


    @DisplayName("Payout: Create Payment")
    @Test
    void CreateWithdrawal() throws JsonProcessingException {
        WithdrawalPayment payout = WithdrawalPayment.create();
        String jsonBody = mapper.writeValueAsString(payout);
        var apiPayout = request.post("/api/v1/payments", RequestOptions.create().setData(jsonBody));
        logger.info("Request Payout 1: JSON");
        logger.info(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(payout));

        assertThat(apiPayout.status()).isEqualTo(HTTP_OK);
        WithdrawalResponse PayoutResponse = JSONMapper.fromJson(apiPayout.text(), WithdrawalResponse.class);

        logger.info(JSONMapper.toPrettyJson(PayoutResponse));
    }


    @DisplayName("Payout: With negative amount")
    @Test
    void WithNegativeAmount() throws JsonProcessingException {


        WithdrawalPayment withNegativeAmount = WithdrawalPayment.create().withNegativeAmount();

        String jsonBody = mapper.writeValueAsString(withNegativeAmount);

        var WithdrawalWithNegativeAmount = request.post("/api/v1/payments", RequestOptions.create().setData(jsonBody));

        logger.info("Request Payout 2: JSON");
        logger.info(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(withNegativeAmount));

        assertThat(WithdrawalWithNegativeAmount.status()).isEqualTo(HTTP_BAD_REQUEST);

        WithdrawalResponse NegativeAmountResponse = JSONMapper.fromJson(WithdrawalWithNegativeAmount.text(), WithdrawalResponse.class);

        logger.info(JSONMapper.toPrettyJson(NegativeAmountResponse));
    }


    @DisplayName("Payout: With no card in request")
    @Test
    void WithoutCard() throws JsonProcessingException {


        WithdrawalPayment withoutCard = WithdrawalPayment.create().withoutCard();

        String jsonBody = mapper.writeValueAsString(withoutCard);

        var APIWithoutCard = request.post("/api/v1/payments", RequestOptions.create().setData(jsonBody));


        logger.info("Request Payout 3: JSON");
        logger.info(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(withoutCard));

        assertThat(APIWithoutCard.status()).isEqualTo(HTTP_BAD_REQUEST);

        WithdrawalResponse ResponseWithoutCard = JSONMapper.fromJson(APIWithoutCard.text(), WithdrawalResponse.class);

        logger.info(JSONMapper.toPrettyJson(ResponseWithoutCard));

    }


    @DisplayName("Payout: With Zero amount")
    @Test
    void WithZeroAmount() throws JsonProcessingException {
        WithdrawalPayment withZeroAmount = WithdrawalPayment.create().withZeroAmount();
        String jsonBody = mapper.writeValueAsString(withZeroAmount);

        var APIZeroAmount = request.post("/api/v1/payments", RequestOptions.create().setData(jsonBody));

        logger.info("Request Payout 4: JSON");
        logger.info(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(withZeroAmount));


        assertThat(APIZeroAmount.status()).isEqualTo(HTTP_BAD_REQUEST);

        WithdrawalResponse ResponseWithZeroAmount = JSONMapper.fromJson(APIZeroAmount.text(), WithdrawalResponse.class);

        logger.info(JSONMapper.toPrettyJson(ResponseWithZeroAmount));

    }

}