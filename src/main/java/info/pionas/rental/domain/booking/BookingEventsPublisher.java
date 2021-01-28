package info.pionas.rental.domain.booking;

import info.pionas.rental.domain.clock.Clock;
import info.pionas.rental.domain.event.EventIdFactory;
import info.pionas.rental.domain.eventchannel.EventChannel;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;


@RequiredArgsConstructor
public class BookingEventsPublisher {
    private final EventIdFactory eventIdFactory;
    private final Clock clock;
    private final EventChannel eventChannel;

    public void bookingAccepted(RentalType rentalType, String rentalPlaceId, String tenantId, List<LocalDate> days) {
        BookingAccepted bookingAccepted = new BookingAccepted(eventIdFactory.create(), clock.now(), rentalType.name(), rentalPlaceId, tenantId, days);
        eventChannel.publish(bookingAccepted);
    }
}
