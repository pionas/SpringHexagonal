package info.pionas.payment.infrastructure.paymentservice;

import info.pionas.payment.domain.payment.PaymentService;
import info.pionas.payment.domain.payment.PaymentStatus;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Primary
@Profile("FakePaymentService")
public class FakePaymentService implements PaymentService {
    private PaymentStatus paymentStatus = PaymentStatus.SUCCESS;

    @Override
    public PaymentStatus transfer(String senderId, String recipientId, BigDecimal amount) {
        return paymentStatus;
    }

    public void change(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}