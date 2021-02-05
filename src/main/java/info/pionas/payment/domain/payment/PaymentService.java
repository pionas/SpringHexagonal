package info.pionas.payment.domain.payment;

import java.math.BigDecimal;

public interface PaymentService {
    PaymentStatus transfer(String senderId, String recipientId, BigDecimal amount);
}
