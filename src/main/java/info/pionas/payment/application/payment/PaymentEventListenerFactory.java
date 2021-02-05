package info.pionas.payment.application.payment;

import info.pionas.payment.domain.event.PaymentEventChannel;
import info.pionas.payment.domain.payment.PaymenEventPublisher;
import info.pionas.payment.domain.payment.PaymentFactory;
import info.pionas.payment.domain.payment.PaymentService;
import info.pionas.rental.domain.clock.Clock;
import info.pionas.rental.domain.event.EventIdFactory;

public class PaymentEventListenerFactory {
    public PaymentEventListener paymentEventListener(PaymentService paymentService, PaymentEventChannel eventChannel, EventIdFactory eventIdFactory, Clock clock) {
        PaymenEventPublisher paymenEventPublisher = new PaymenEventPublisher(eventChannel, eventIdFactory, clock);
        PaymentFactory paymentFactory = new PaymentFactory(paymentService, paymenEventPublisher);
        return new PaymentEventListener(paymentFactory);
    }
}
