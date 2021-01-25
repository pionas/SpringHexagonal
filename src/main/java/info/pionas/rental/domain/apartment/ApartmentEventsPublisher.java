package info.pionas.rental.domain.apartment;

import info.pionas.rental.domain.event.EventIdFactory;
import info.pionas.rental.domain.eventchannel.EventChannel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ApartmentEventsPublisher {
    private final EventIdFactory eventIdFactory;
    private final EventChannel eventChannel;

    public void publishApartmentBooked(String apartmentId, String ownerId, String tenantId, Period period) {
        ApartmentBooked apartmentBooked = ApartmentBooked.create(eventIdFactory.create(), apartmentId, ownerId, tenantId, period);
        eventChannel.publish(apartmentBooked);
    }
}
