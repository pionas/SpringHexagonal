package info.pionas.payment.application.payment;

import info.pionas.payment.domain.event.PaymentEventChannel;
import info.pionas.payment.domain.payment.PaymentEventPublisher;
import info.pionas.payment.domain.payment.PaymentFactory;
import info.pionas.payment.domain.payment.PaymentService;
import info.pionas.rental.domain.clock.Clock;
import info.pionas.rental.domain.event.EventIdFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentEventListenerFactory {
    @Bean
    public PaymentEventListener paymentEventListener(PaymentService paymentService, PaymentEventChannel eventChannel, EventIdFactory eventIdFactory, Clock clock) {
        PaymentEventPublisher paymentEventPublisher = new PaymentEventPublisher(eventChannel, eventIdFactory, clock);
        PaymentFactory paymentFactory = new PaymentFactory(paymentService, paymentEventPublisher);
        return new PaymentEventListener(paymentFactory);
    }
}
