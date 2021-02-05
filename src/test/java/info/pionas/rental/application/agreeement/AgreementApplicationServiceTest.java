package info.pionas.rental.application.agreeement;

import info.pionas.rental.domain.agreeement.AgreementAccepted;
import info.pionas.rental.domain.agreeement.AgreementAcceptedAssertion;
import info.pionas.rental.domain.agreement.Agreement;
import info.pionas.rental.domain.agreement.AgreementRepository;
import info.pionas.rental.domain.event.FakeEventIdFactory;
import info.pionas.rental.domain.eventchannel.EventChannel;
import info.pionas.rental.domain.money.Money;
import info.pionas.rental.domain.rentalplaceidentifier.RentalType;
import info.pionas.rental.infrastructure.clock.FakeClock;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static info.pionas.rental.domain.agreement.Agreement.Builder.agreement;
import static java.util.Arrays.asList;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

class AgreementApplicationServiceTest {
    private static final RentalType RENTAL_TYPE = RentalType.APARTMENT;
    private static final String RENTAL_PLACE_ID = "5748";
    private static final String OWNER_ID = "4346";
    private static final String TENANT_ID = "1234";
    public static final BigDecimal PRICE = BigDecimal.valueOf(123.45);
    private static final List<LocalDate> DAYS_1 = asList(LocalDate.now(), LocalDate.now().plusDays(1));
    private static final UUID AGREEMENT_ID = UUID.randomUUID();
    private final EventChannel eventChannel = mock(EventChannel.class);
    private final AgreementRepository agreementRepository = mock(AgreementRepository.class);
    private final AgreementApplicationService service = new AgreementApplicationServiceFactory()
            .agreementApplicationService(agreementRepository, eventChannel, new FakeEventIdFactory(), new FakeClock());

    @Test
    void shouldAcceptAgreement() {
        givenExistingAgreement();
        ArgumentCaptor<AgreementAccepted> captor = ArgumentCaptor.forClass(AgreementAccepted.class);

        service.accept(AGREEMENT_ID);

        then(eventChannel).should().publish(captor.capture());
        AgreementAcceptedAssertion.assertThat(captor.getValue())
                .hasEventId(FakeEventIdFactory.UUID)
                .hasEventCreationDateTime(FakeClock.NOW)
                .hasRentalType("APARTMENT")
                .hasRentalPlaceId(RENTAL_PLACE_ID)
                .hasOwnerId(OWNER_ID)
                .hasTenantId(TENANT_ID)
                .hasPrice(PRICE)
                .hasDays(DAYS_1);
    }

    private void givenExistingAgreement() {
        Agreement agreement = agreement()
                .withRentalType(RENTAL_TYPE)
                .withRentalPlaceId(RENTAL_PLACE_ID)
                .withOwnerId(OWNER_ID)
                .withTenantId(TENANT_ID)
                .withPrice(Money.of(PRICE))
                .withDays(DAYS_1)
                .build();

        given(agreementRepository.findById(AGREEMENT_ID)).willReturn(agreement);
    }
}