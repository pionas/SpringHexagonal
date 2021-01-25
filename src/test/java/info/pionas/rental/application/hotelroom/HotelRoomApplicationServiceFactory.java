package info.pionas.rental.application.hotelroom;

import info.pionas.rental.domain.apartment.BookingRepository;
import info.pionas.rental.domain.eventchannel.EventChannel;
import info.pionas.rental.domain.hotel.HotelRepository;
import info.pionas.rental.domain.hotelroom.HotelRoomRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class HotelRoomApplicationServiceFactory {
    @Bean
    HotelRoomApplicationService create(HotelRepository hotelRepository, HotelRoomRepository hotelRoomRepository, BookingRepository bookingRepository, EventChannel eventChannel) {
        return new HotelRoomApplicationService(hotelRepository, hotelRoomRepository, bookingRepository, eventChannel);
    }
}
