package info.pionas.rental.application.hotel;

import info.pionas.rental.domain.booking.BookingRepository;
import info.pionas.rental.domain.clock.Clock;
import info.pionas.rental.domain.event.EventIdFactory;
import info.pionas.rental.domain.eventchannel.EventChannel;
import info.pionas.rental.domain.hotel.HotelEventsPublisher;
import info.pionas.rental.domain.hotel.HotelRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class HotelApplicationServiceFactory {
    @Bean
    @SuppressWarnings("checkstyle:ParameterNumber")
    HotelApplicationService hotelApplicationService(
            HotelRepository hotelRepository, BookingRepository bookingRepository, EventIdFactory eventIdFactory, Clock clock, EventChannel eventChannel) {
        HotelEventsPublisher hotelEventsPublisher = new HotelEventsPublisher(eventIdFactory, clock, eventChannel);

        return new HotelApplicationService(hotelRepository, bookingRepository, hotelEventsPublisher);
    }
}
