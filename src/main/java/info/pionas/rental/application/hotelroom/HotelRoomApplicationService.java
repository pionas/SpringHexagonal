package info.pionas.rental.application.hotelroom;

import info.pionas.rental.domain.booking.Booking;
import info.pionas.rental.domain.booking.BookingRepository;
import info.pionas.rental.domain.hotelroom.HotelRoom;
import info.pionas.rental.domain.hotelroom.HotelRoomEventsPublisher;
import info.pionas.rental.domain.hotelroom.HotelRoomFactory;
import info.pionas.rental.domain.hotelroom.HotelRoomRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class HotelRoomApplicationService {

    private final HotelRoomRepository hotelRoomRepository;
    private final BookingRepository bookingRepository;
    private final HotelRoomEventsPublisher hotelRoomEventsPublisher;

    public String add(String hotelId, int number, Map<String, Double> spacesDefinition, String description) {
        HotelRoom hotelRoom = new HotelRoomFactory().create(hotelId, number, spacesDefinition, description);
        return hotelRoomRepository.save(hotelRoom);
    }

    public String book(String id, String tenantId, List<LocalDate> days) {
        HotelRoom hotelRoom = hotelRoomRepository.findById(id);
        Booking booking = hotelRoom.book(tenantId, days, hotelRoomEventsPublisher);
        return bookingRepository.save(booking);
    }
}
