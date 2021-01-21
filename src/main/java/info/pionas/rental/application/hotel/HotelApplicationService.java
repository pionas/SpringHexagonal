package info.pionas.rental.application.hotel;

import info.pionas.rental.domain.hotel.Hotel;
import info.pionas.rental.domain.hotel.HotelFactory;
import info.pionas.rental.domain.hotel.HotelRepository;
import lombok.RequiredArgsConstructor;

/**
 * @author Adi
 */
@RequiredArgsConstructor
public class HotelApplicationService {

    private final HotelRepository hotelRepository;

    public void add(String name, String street, String postalCode, String buildingNumber, String city, String country) {
        Hotel hotel = new HotelFactory().create(name, street, postalCode, buildingNumber, city, country);
        hotelRepository.save(hotel);
    }
}
