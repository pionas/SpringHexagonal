package info.pionas.payment.domain.payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentCompletedTestFactory {
    public static PaymentCompleted create(String eventId, LocalDateTime creationTime, String senderId, String recipientId, BigDecimal amount) {
        return new PaymentCompleted(eventId, creationTime, senderId, recipientId, amount);
    }
}