package info.pionas.payment.domain.payment;

import info.pionas.payment.domain.event.PaymentEventChannel;
import info.pionas.rental.domain.clock.Clock;
import info.pionas.rental.domain.event.EventIdFactory;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class PaymenEventPublisher {
    private final PaymentEventChannel eventChannel;
    private final EventIdFactory eventIdFactory;
    private final Clock clock;

    void paymentCompleted(String senderId, String recipientId, BigDecimal totalAmount) {
        PaymentCompleted event = new PaymentCompleted(eventIdFactory.create(), clock.now(), senderId, recipientId, totalAmount);
        eventChannel.publish(event);
    }

    void paymentFailed(String senderId, String recipientId, BigDecimal totalAmount) {
        PaymentFailed event = new PaymentFailed(eventIdFactory.create(), clock.now(), senderId, recipientId, totalAmount);
        eventChannel.publish(event);
    }
}
