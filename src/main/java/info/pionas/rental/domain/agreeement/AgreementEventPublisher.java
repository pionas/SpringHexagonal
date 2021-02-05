package info.pionas.rental.domain.agreeement;

import info.pionas.rental.domain.clock.Clock;
import info.pionas.rental.domain.event.EventIdFactory;
import info.pionas.rental.domain.eventchannel.EventChannel;
import info.pionas.rental.domain.money.Money;
import info.pionas.rental.domain.rentalplaceidentifier.RentalType;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class AgreementEventPublisher {
    private final EventChannel eventChannel;
    private final EventIdFactory eventIdFactory;
    private final Clock clock;

    public void agreementAccepted(RentalType rentalType, String rentalPlaceId, String ownerId, String tenantId, List<LocalDate> days, Money price) {
        AgreementAccepted event = new AgreementAccepted(
                eventIdFactory.create(), clock.now(), rentalType.name(), rentalPlaceId, ownerId, tenantId, days, price.getValue());
        eventChannel.publish(event);
    }
}
