package info.pionas.payment.infrastructure.paymentservice;

import info.pionas.payment.domain.payment.PaymentService;
import info.pionas.payment.domain.payment.PaymentStatus;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static info.pionas.payment.domain.payment.PaymentStatus.NOT_ENOUGH_MONEY;
import static info.pionas.payment.domain.payment.PaymentStatus.SUCCESS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Tests added for coverage because RestPaymentClientIntegrationTest are commented out due to lack of Payment Service
 * on any other environment than local
 */
@Tag("IntegrationTest")
class RestPaymentClientFakeIntegrationTest {
    private static final String URL = "http://localhost:8104";
    private static final String SENDER_ID = "12345";
    private static final String RECIPIENT_ID = "67890";
    private static final BigDecimal AMOUNT = BigDecimal.valueOf(123.45);

    @Test
    void shouldCreateRestPaymentClient() {
        PaymentService actual = new RestPaymentClientFactory().paymentService(URL);

        assertThat(actual).isInstanceOf(RestPaymentClient.class);
    }

    @Test
    void shouldPaySuccessfully() {
        PaymentService service = givenPaymentService("SUCCESS");

        PaymentStatus actual = service.transfer(SENDER_ID, RECIPIENT_ID, AMOUNT);

        assertThat(actual).isEqualTo(SUCCESS);
    }

    @Test
    void shouldRecognizeThereIsNotEnoughMoney() {
        PaymentService service = givenPaymentService("NOT_ENOUGH_RESOURCES");

        PaymentStatus actual = service.transfer(SENDER_ID, RECIPIENT_ID, AMOUNT);

        assertThat(actual).isEqualTo(NOT_ENOUGH_MONEY);
    }

    private PaymentService givenPaymentService(String status) {
        RestTemplate restTemplate = mock(RestTemplate.class);
        PaymentRequest request = new PaymentRequest(SENDER_ID, RECIPIENT_ID, AMOUNT);
        given(restTemplate.postForObject(URL.concat("/payment"), request, PaymentResponse.class)).willReturn(new PaymentResponse(status));

        return new RestPaymentClient(restTemplate, URL);
    }
}