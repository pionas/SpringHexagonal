package info.pionas.payment.domain.payment;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
public class PaymentCompleted {
    private final String eventId;
    private final LocalDateTime eventCreationDateTime;
    private final String senderId;
    private final String recipientId;
    private final BigDecimal amount;
}
