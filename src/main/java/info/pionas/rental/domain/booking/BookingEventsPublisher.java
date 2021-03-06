package info.pionas.rental.domain.booking;

import info.pionas.rental.domain.clock.Clock;
import info.pionas.rental.domain.event.EventIdFactory;
import info.pionas.rental.domain.eventchannel.EventChannel;
import info.pionas.rental.domain.rentalplaceidentifier.RentalType;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;


@RequiredArgsConstructor
public class BookingEventsPublisher {
    private final EventIdFactory eventIdFactory;
    private final Clock clock;
    private final EventChannel eventChannel;

    void bookingAccepted(RentalType rentalType, String rentalPlaceId, String tenantId, List<LocalDate> days) {
        BookingAccepted bookingAccepted = new BookingAccepted(eventIdFactory.create(), clock.now(), rentalType.name(), rentalPlaceId, tenantId, days);
        eventChannel.publish(bookingAccepted);
    }

    void bookingRejected(RentalType rentalType, String rentalPlaceId, String tenantId, List<LocalDate> days) {
        BookingRejected bookingRejected = new BookingRejected(eventIdFactory.create(), clock.now(), rentalType.name(), rentalPlaceId, tenantId, days);
        eventChannel.publish(bookingRejected);
    }
}
