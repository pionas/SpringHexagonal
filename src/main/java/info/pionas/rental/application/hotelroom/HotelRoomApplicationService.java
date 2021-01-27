package info.pionas.rental.application.hotelroom;

import info.pionas.rental.domain.booking.Booking;
import info.pionas.rental.domain.booking.BookingRepository;
import info.pionas.rental.domain.hotel.HotelRepository;
import info.pionas.rental.domain.hotelroom.HotelRoom;
import info.pionas.rental.domain.hotelroom.HotelRoomEventsPublisher;
import info.pionas.rental.domain.hotelroom.HotelRoomRepository;
import lombok.RequiredArgsConstructor;

import static info.pionas.rental.domain.hotelroom.HotelRoom.Builder.hotelRoom;

@RequiredArgsConstructor
public class HotelRoomApplicationService {

    private final HotelRepository hotelRepository;
    private final HotelRoomRepository hotelRoomRepository;
    private final BookingRepository bookingRepository;
    private final HotelRoomEventsPublisher hotelRoomEventsPublisher;

    public String add(HotelRoomDto hotelRoomDto) {
        hotelRepository.findById(hotelRoomDto.getHotelId());
        HotelRoom hotelRoom = hotelRoom()
                .withHotelId(hotelRoomDto.getHotelId())
                .withNumber(hotelRoomDto.getNumber())
                .withSpacesDefinition(hotelRoomDto.getSpacesDefinition())
                .withDescription(hotelRoomDto.getDescription())
                .build();

        return hotelRoomRepository.save(hotelRoom);
    }

    public String book(HotelRoomBookingDto hotelRoomBookingDto) {
        HotelRoom hotelRoom = hotelRoomRepository.findById(hotelRoomBookingDto.getHotelRoomId());
        Booking booking = hotelRoom.book(hotelRoomBookingDto.getTenantId(), hotelRoomBookingDto.getDays(), hotelRoomEventsPublisher);

        return bookingRepository.save(booking);
    }
}
