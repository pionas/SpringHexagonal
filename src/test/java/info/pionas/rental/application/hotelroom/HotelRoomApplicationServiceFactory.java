package info.pionas.rental.application.hotelroom;

import info.pionas.rental.domain.booking.BookingRepository;
import info.pionas.rental.domain.clock.Clock;
import info.pionas.rental.domain.event.EventIdFactory;
import info.pionas.rental.domain.eventchannel.EventChannel;
import info.pionas.rental.domain.hotelroom.HotelRoomEventsPublisher;
import info.pionas.rental.domain.hotelroom.HotelRoomRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class HotelRoomApplicationServiceFactory {
    @Bean
    HotelRoomApplicationService hotelRoomApplicationService(HotelRoomRepository hotelRoomRepository, BookingRepository bookingRepository, EventChannel eventChannel) {
        HotelRoomEventsPublisher hotelRoomEventsPublisher = new HotelRoomEventsPublisher(new EventIdFactory(), new Clock(), eventChannel);
        return new HotelRoomApplicationService(hotelRoomRepository, bookingRepository, hotelRoomEventsPublisher);
    }
}
