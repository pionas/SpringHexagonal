package info.pionas.rental.application.hotelroom;

import info.pionas.rental.domain.apartment.BookingRepository;
import info.pionas.rental.domain.clock.Clock;
import info.pionas.rental.domain.event.EventIdFactory;
import info.pionas.rental.domain.eventchannel.EventChannel;
import info.pionas.rental.domain.hotel.HotelRepository;
import info.pionas.rental.domain.hotelroom.HotelRoomEventsPublisher;
import info.pionas.rental.domain.hotelroom.HotelRoomRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class HotelRoomApplicationServiceFactory {
    @Bean
    HotelRoomApplicationService hotelRoomApplicationService(HotelRepository hotelRepository, HotelRoomRepository hotelRoomRepository, BookingRepository bookingRepository, EventChannel eventChannel) {
        HotelRoomEventsPublisher hotelRoomEventsPublisher = new HotelRoomEventsPublisher(new EventIdFactory(), new Clock(), eventChannel);
        return new HotelRoomApplicationService(hotelRepository, hotelRoomRepository, bookingRepository, hotelRoomEventsPublisher);
    }
}
