package info.pionas.rental.application.booking;

import info.pionas.rental.domain.booking.BookingRepository;
import info.pionas.rental.domain.eventchannel.EventChannel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class BookingCommandHandlerFactory {
    @Bean
    BookingCommandHandler bookingCommandHandler(BookingRepository bookingRepository, EventChannel eventChannel) {
        return new BookingCommandHandler(bookingRepository, eventChannel);
    }
}