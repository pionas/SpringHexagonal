package info.pionas.payment.infrastructure.paymentservice;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

class PaymentRequestTest {
    private static final String SENDER_ID = "12345";
    private static final String RECIPIENT_ID = "67890";
    private static final BigDecimal AMOUNT = BigDecimal.valueOf(123.45);

    @Test
    void shouldRecognizeTheSameInstanceAsTheSameAggregate() {
        PaymentRequest actual = givenPaymentRequest();

        Assertions.assertThat(actual.equals(actual)).isTrue();
        Assertions.assertThat(actual.hashCode()).isEqualTo(actual.hashCode());
    }

    @Test
    void shouldRecognizeNullIsNotTheSameAsPaymentRequest() {
        PaymentRequest actual = givenPaymentRequest();

        Assertions.assertThat(actual.equals(null)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("notTeSamePaymentRequests")
    void shouldRecognizePaymentRequestsDoesNotRepresentTheSameAggregate(Object toCompare) {
        PaymentRequest actual = givenPaymentRequest();

        Assertions.assertThat(actual.equals(toCompare)).isFalse();
        Assertions.assertThat(actual.hashCode()).isNotEqualTo(toCompare.hashCode());
    }

    private PaymentRequest givenPaymentRequest() {
        return new PaymentRequest(SENDER_ID, RECIPIENT_ID, AMOUNT);
    }

    private static Stream<Object> notTeSamePaymentRequests() {
        return Stream.of(
                new PaymentRequest("12", RECIPIENT_ID, AMOUNT),
                new Object()
        );
    }
}