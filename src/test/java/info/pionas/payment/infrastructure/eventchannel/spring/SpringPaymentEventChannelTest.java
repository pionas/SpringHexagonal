package info.pionas.payment.infrastructure.eventchannel.spring;

import info.pionas.payment.domain.event.PaymentEventChannel;
import info.pionas.payment.domain.payment.PaymentCompleted;
import info.pionas.payment.domain.payment.PaymentCompletedTestFactory;
import info.pionas.payment.domain.payment.PaymentFailed;
import info.pionas.payment.domain.payment.PaymentFailedTestFactory;
import info.pionas.rental.domain.rentalplaceidentifier.RentalType;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

class SpringPaymentEventChannelTest {
    private static final String SENDER_ID = "4346";
    private static final String RECIPIENT_ID = "1234";
    public static final BigDecimal PRICE = BigDecimal.valueOf(100.00);
    public static final BigDecimal AMOUNT = BigDecimal.valueOf(200.00);
    private static final List<LocalDate> DAYS = asList(LocalDate.now(), LocalDate.now().plusDays(1));
    private static final String EVENT_ID = "213213";
    private static final LocalDateTime CREATION_TIME = LocalDateTime.now();

    private final ApplicationEventPublisher applicationEventPublisher = mock(ApplicationEventPublisher.class);
    private final PaymentEventChannel eventChannel = new SpringPaymentEventChannel(applicationEventPublisher);

    @Test
    void shouldPublishPaymentCompleted() {
        PaymentCompleted paymentCompleted = PaymentCompletedTestFactory.create(EVENT_ID, CREATION_TIME, SENDER_ID, RECIPIENT_ID, AMOUNT);

        eventChannel.publish(paymentCompleted);

        then(applicationEventPublisher).should().publishEvent(paymentCompleted);
    }

    @Test
    void shouldPublishPaymentFailed() {
        PaymentFailed paymentFailed = PaymentFailedTestFactory.create(EVENT_ID, CREATION_TIME, SENDER_ID, RECIPIENT_ID, AMOUNT);

        eventChannel.publish(paymentFailed);

        then(applicationEventPublisher).should().publishEvent(paymentFailed);
    }
}