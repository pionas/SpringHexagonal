package info.pionas.rental.domain.apartment;

import info.pionas.rental.domain.clock.Clock;
import info.pionas.rental.domain.event.EventIdFactory;
import info.pionas.rental.domain.eventchannel.EventChannel;
import info.pionas.rental.domain.period.Period;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ApartmentEventsPublisher {
    private final EventIdFactory eventIdFactory;
    private final Clock clock;
    private final EventChannel eventChannel;

    public void publishApartmentBooked(String apartmentId, String ownerId, String tenantId, Period period) {
        ApartmentBooked apartmentBooked = new ApartmentBooked(eventIdFactory.create(), clock.now(), apartmentId, ownerId, tenantId, period.getStart(), period.getEnd());
        eventChannel.publish(apartmentBooked);
    }
}
