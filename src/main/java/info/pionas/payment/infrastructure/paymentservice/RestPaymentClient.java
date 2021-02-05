package info.pionas.payment.infrastructure.paymentservice;

import info.pionas.payment.domain.payment.PaymentService;
import info.pionas.payment.domain.payment.PaymentStatus;

import java.math.BigDecimal;

public class RestPaymentClient implements PaymentService {
    @Override
    public PaymentStatus transfer(String senderId, String recipientId, BigDecimal amount) {
        return PaymentStatus.SUCCESS;
    }
}
