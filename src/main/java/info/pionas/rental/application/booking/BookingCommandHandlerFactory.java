package info.pionas.rental.application.booking;

import info.pionas.rental.domain.booking.BookingEventsPublisher;
import info.pionas.rental.domain.booking.BookingRepository;
import info.pionas.rental.domain.clock.Clock;
import info.pionas.rental.domain.event.EventIdFactory;
import info.pionas.rental.domain.eventchannel.EventChannel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class BookingCommandHandlerFactory {
    @Bean
    BookingCommandHandler bookingCommandHandler(BookingRepository bookingRepository, EventIdFactory eventIdFactory, Clock clock, EventChannel eventChannel) {
        return new BookingCommandHandler(bookingRepository, new BookingEventsPublisher(eventIdFactory, clock, eventChannel));
    }
}
