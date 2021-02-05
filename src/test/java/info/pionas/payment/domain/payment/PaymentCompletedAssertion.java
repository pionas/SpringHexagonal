package info.pionas.payment.domain.payment;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class PaymentCompletedAssertion {
    private final PaymentCompleted actual;

    public static PaymentCompletedAssertion assertThat(PaymentCompleted actual) {
        return new PaymentCompletedAssertion(actual);
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
