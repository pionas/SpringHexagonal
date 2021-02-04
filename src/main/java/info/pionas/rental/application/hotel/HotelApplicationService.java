package info.pionas.rental.application.hotel;

import info.pionas.rental.domain.booking.Booking;
import info.pionas.rental.domain.booking.BookingRepository;
import info.pionas.rental.domain.hotel.Hotel;
import info.pionas.rental.domain.hotel.HotelEventsPublisher;
import info.pionas.rental.domain.hotel.HotelRepository;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import static info.pionas.rental.domain.hotel.Hotel.Builder.hotel;

@RequiredArgsConstructor
public class HotelApplicationService {

    private final HotelRepository hotelRepository;
    private final BookingRepository bookingRepository;
    private final HotelEventsPublisher hotelEventsPublisher;

    public String add(HotelDto hotelDto) {
        Hotel hotel = hotel()
                .withName(hotelDto.getName())
                .withStreet(hotelDto.getStreet())
                .withPostalCode(hotelDto.getPostalCode())
                .withBuildingNumber(hotelDto.getBuildingNumber())
                .withCity(hotelDto.getCity())
                .withCountry(hotelDto.getCountry())
                .build();

        return hotelRepository.save(hotel);
    }

    public void add(HotelRoomDto hotelRoomDto) {
        Hotel hotel = hotelRepository.findById(hotelRoomDto.getHotelId());

        hotel.addRoom(hotelRoomDto.getNumber(), hotelRoomDto.getSpacesDefinition(), hotelRoomDto.getDescription());

        hotelRepository.save(hotel);
    }

    public UUID book(HotelRoomBookingDto hotelRoomBookingDto) {
        Hotel hotel = hotelRepository.findById(hotelRoomBookingDto.getHotelId());

        Booking booking = hotel.bookRoom(
                hotelRoomBookingDto.getNumber(), hotelRoomBookingDto.getTenantId(), hotelRoomBookingDto.getDays(), hotelEventsPublisher);

        return bookingRepository.save(booking);
    }
}
