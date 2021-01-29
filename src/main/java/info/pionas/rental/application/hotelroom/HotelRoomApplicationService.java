package info.pionas.rental.application.hotelroom;

import info.pionas.rental.domain.booking.Booking;
import info.pionas.rental.domain.booking.BookingRepository;
import info.pionas.rental.domain.hotel.Hotel;
import info.pionas.rental.domain.hotel.HotelRepository;
import info.pionas.rental.domain.hotel.HotelRoomEventsPublisher;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HotelRoomApplicationService {

    private final HotelRepository hotelRepository;
    private final BookingRepository bookingRepository;
    private final HotelRoomEventsPublisher hotelRoomEventsPublisher;

    public String add(HotelRoomDto hotelRoomDto) {
        Hotel hotel = hotelRepository.findById(hotelRoomDto.getHotelId());
        hotel.addRoom(
                hotelRoomDto.getNumber(),
                hotelRoomDto.getSpacesDefinition(),
                hotelRoomDto.getDescription()
        );
        hotelRepository.save(hotel);
        return hotel.getIdOfRoom(hotelRoomDto.getNumber());
    }

    public String book(HotelRoomBookingDto hotelRoomBookingDto) {
        Hotel hotel = hotelRepository.findById(hotelRoomBookingDto.getHotelId());
        Booking booking = hotel.bookRoom(hotelRoomBookingDto.getNumber(), hotelRoomBookingDto.getTenantId(), hotelRoomBookingDto.getDays(), hotelRoomEventsPublisher);

        return bookingRepository.save(booking);
    }
}
