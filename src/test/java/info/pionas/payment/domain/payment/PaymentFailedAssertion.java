package info.pionas.payment.domain.payment;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class PaymentFailedAssertion {
    private final PaymentFailed actual;

    public static PaymentFailedAssertion assertThat(PaymentFailed actual) {
        return new PaymentFailedAssertion(actual);
    }

    public PaymentFailedAssertion hasEventId() {
        Assertions.assertThat(actual.getEventId()).matches(Pattern.compile("[0-9a-z\\-]{36}"));
        return this;
    }

    public PaymentFailedAssertion hasEventCreationDateTime(LocalDateTime expected) {
        Assertions.assertThat(actual.getEventCreationDateTime()).isEqualTo(expected);
        return this;
    }

    public PaymentFailedAssertion hasSenderId(String expected) {
        Assertions.assertThat(actual.getSenderId()).isEqualTo(expected);
        return this;
    }

    public PaymentFailedAssertion hasRecipientId(String expected) {
        Assertions.assertThat(actual.getRecipientId()).isEqualTo(expected);
        return this;
    }

    public PaymentFailedAssertion hasAmount(BigDecimal expected) {
        Assertions.assertThat(actual.getAmount()).isEqualTo(expected);
        return this;
    }
}
