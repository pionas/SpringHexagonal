package info.pionas.payment.domain.event;

import info.pionas.payment.domain.payment.PaymentCompleted;
import info.pionas.payment.domain.payment.PaymentFailed;

public interface PaymentEventChannel {
    void publish(PaymentCompleted paymentCompleted);

    void publish(PaymentFailed paymentFailed);
}
