package info.pionas.payment.application.payment;

import info.pionas.payment.domain.event.PaymentEventChannel;
import info.pionas.payment.domain.payment.PaymentCompleted;
import info.pionas.payment.domain.payment.PaymentCompletedAssertion;
import info.pionas.payment.domain.payment.PaymentFailed;
import info.pionas.payment.domain.payment.PaymentFailedAssertion;
import info.pionas.payment.infrastructure.paymentservice.FakePaymentService;
import info.pionas.rental.domain.agreement.AgreementAccepted;
import info.pionas.rental.domain.agreement.AgreementAcceptedTestFactory;
import info.pionas.rental.domain.event.FakeEventIdFactory;
import info.pionas.rental.domain.eventchannel.EventChannel;
import info.pionas.rental.domain.rentalplaceidentifier.RentalType;
import info.pionas.rental.infrastructure.clock.FakeClock;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static info.pionas.payment.domain.payment.PaymentStatus.NOT_ENOUGH_MONEY;
import static info.pionas.payment.domain.payment.PaymentStatus.SUCCESS;
import static java.util.Arrays.asList;
import static org.mockito.BDDMockito.then;

@SpringBootTest
@Tag("IntegrationTest")
@ActiveProfiles("FakePaymentService")
class PaymentEventListenerIntegrationTest {
    private static final String EVENT_ID = FakeEventIdFactory.UUID;
    private static final LocalDateTime CREATION_TIME = FakeClock.NOW;
    private static final String RENTAL_TYPE = RentalType.APARTMENT.name();
    private static final String RENTAL_PLACE_ID = "5748";
    private static final String OWNER_ID = "4346";
    private static final String TENANT_ID = "1234";
    public static final BigDecimal PRICE = BigDecimal.valueOf(100.00);
    public static final BigDecimal AMOUNT = BigDecimal.valueOf(200.00);
    private static final List<LocalDate> DAYS = asList(LocalDate.now(), LocalDate.now().plusDays(1));

    @Autowired
    private EventChannel eventChannel;
    @Autowired
    private FakePaymentService restPaymentClient;
    @SpyBean
    private PaymentEventChannel paymentEventChannel;

    @Test
    void shouldConsumePublishedAgreementAcceptedAndPublishPaymentCompleted() {
        restPaymentClient.change(SUCCESS);
        ArgumentCaptor<PaymentCompleted> captor = ArgumentCaptor.forClass(PaymentCompleted.class);

        eventChannel.publish(givenAgreementAccepted());

        then(paymentEventChannel).should().publish(captor.capture());
        PaymentCompletedAssertion.assertThat(captor.getValue())
                .hasEventId()
                .hasSenderId(TENANT_ID)
                .hasRecipientId(OWNER_ID)
                .hasAmount(AMOUNT);
    }

    @Test
    void shouldConsumePublishedAgreementAcceptedAndPublishPaymentFailed() {
        restPaymentClient.change(NOT_ENOUGH_MONEY);
        ArgumentCaptor<PaymentFailed> captor = ArgumentCaptor.forClass(PaymentFailed.class);

        eventChannel.publish(givenAgreementAccepted());

        then(paymentEventChannel).should().publish(captor.capture());
        PaymentFailedAssertion.assertThat(captor.getValue())
                .hasEventId()
                .hasSenderId(TENANT_ID)
                .hasRecipientId(OWNER_ID)
                .hasAmount(AMOUNT);
    }

    private AgreementAccepted givenAgreementAccepted() {
        return AgreementAcceptedTestFactory.create(
                EVENT_ID, CREATION_TIME, RENTAL_TYPE, RENTAL_PLACE_ID, OWNER_ID, TENANT_ID, DAYS, PRICE);
    }
}