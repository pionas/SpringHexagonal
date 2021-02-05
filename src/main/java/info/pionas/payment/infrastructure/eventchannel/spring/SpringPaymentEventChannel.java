package info.pionas.payment.infrastructure.eventchannel.spring;

import info.pionas.payment.domain.event.PaymentEventChannel;
import info.pionas.payment.domain.payment.PaymentCompleted;
import info.pionas.payment.domain.payment.PaymentFailed;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpringPaymentEventChannel implements PaymentEventChannel {

    private final ApplicationEventPublisher publisher;

    @Override
    public void publish(PaymentCompleted paymentCompleted) {
        publisher.publishEvent(paymentCompleted);
    }

    @Override
    public void publish(PaymentFailed paymentFailed) {
        publisher.publishEvent(paymentFailed);
    }
}
