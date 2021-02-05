package info.pionas.payment.domain.payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentFailedTestFactory {
    public static PaymentFailed create(String eventId, LocalDateTime creationTime, String senderId, String recipientId, BigDecimal amount) {
        return new PaymentFailed(eventId, creationTime, senderId, recipientId, amount);
    }
}