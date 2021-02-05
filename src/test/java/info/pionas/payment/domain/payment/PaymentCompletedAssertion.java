package info.pionas.payment.domain.payment;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class PaymentCompletedAssertion {
    private final PaymentCompleted actual;

    public static PaymentCompletedAssertion assertThat(PaymentCompleted actual) {
        return new PaymentCompletedAssertion(actual);
    }

    public PaymentCompletedAssertion hasEventId() {
        Assertions.assertThat(actual.getEventId()).matches(Pattern.compile("[0-9a-z\\-]{36}"));
        return this;
    }

    public PaymentCompletedAssertion hasEventCreationDateTime(LocalDateTime expected) {
        Assertions.assertThat(actual.getEventCreationDateTime()).isEqualTo(expected);
        return this;
    }

    public PaymentCompletedAssertion hasSenderId(String expected) {
        Assertions.assertThat(actual.getSenderId()).isEqualTo(expected);
        return this;
    }

    public PaymentCompletedAssertion hasRecipientId(String expected) {
        Assertions.assertThat(actual.getRecipientId()).isEqualTo(expected);
        return this;
    }

    public PaymentCompletedAssertion hasAmount(BigDecimal expected) {
        Assertions.assertThat(actual.getAmount()).isEqualTo(expected);
        return this;
    }
}
