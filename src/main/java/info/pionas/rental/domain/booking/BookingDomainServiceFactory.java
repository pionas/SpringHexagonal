package info.pionas.rental.domain.booking;

import info.pionas.rental.domain.clock.Clock;
import info.pionas.rental.domain.event.EventIdFactory;
import info.pionas.rental.domain.eventchannel.EventChannel;

public class BookingDomainServiceFactory {
    public BookingDomainService create(EventIdFactory eventIdFactory, Clock clock, EventChannel eventChannel) {
        BookingEventsPublisher bookingEventsPublisher = new BookingEventsPublisher(eventIdFactory, clock, eventChannel);
        return new BookingDomainService(bookingEventsPublisher);
    }
}
