package info.pionas.payment.infrastructure.paymentservice;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Getter
public class PaymentRequest {
    private final String senderId;
    private final String recipientId;
    private final BigDecimal amount;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PaymentRequest that = (PaymentRequest) o;

        return new EqualsBuilder()
                .append(getSenderId(), that.senderId)
                .append(getRecipientId(), that.recipientId)
                .append(getAmount(), that.amount)
                .isEquals();
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getSenderId())
                .append(getRecipientId())
                .append(getAmount())
                .toHashCode();
    }
}
