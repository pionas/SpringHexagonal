package info.pionas.rental.application.hotelroom;

import info.pionas.rental.domain.booking.BookingRepository;
import info.pionas.rental.domain.event.FakeEventIdFactory;
import info.pionas.rental.domain.eventchannel.EventChannel;
import info.pionas.rental.domain.hotel.HotelRepository;
import info.pionas.rental.domain.hotelroom.HotelRoomEventsPublisher;
import info.pionas.rental.domain.hotelroom.HotelRoomRepository;
import info.pionas.rental.infrastructure.clock.FakeClock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class HotelRoomApplicationServiceFactory {
    @Bean
    HotelRoomApplicationService hotelRoomApplicationService(HotelRepository hotelRepository, HotelRoomRepository hotelRoomRepository, BookingRepository bookingRepository, EventChannel eventChannel) {
        HotelRoomEventsPublisher hotelRoomEventsPublisher = new HotelRoomEventsPublisher(new FakeEventIdFactory(), new FakeClock(), eventChannel);
        return new HotelRoomApplicationService(hotelRepository, hotelRoomRepository, bookingRepository, hotelRoomEventsPublisher);
    }
}
