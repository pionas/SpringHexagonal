package info.pionas.rental.application.hotel;

import info.pionas.rental.domain.hotel.Hotel;
import info.pionas.rental.domain.hotel.HotelFactory;
import info.pionas.rental.domain.hotel.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HotelApplicationService {

    private final HotelRepository hotelRepository;

    public String add(HotelDto hotelDto) {
        Hotel hotel = new HotelFactory().create(
                hotelDto.getName(),
                hotelDto.getStreet(),
                hotelDto.getPostalCode(),
                hotelDto.getBuildingNumber(),
                hotelDto.getCity(),
                hotelDto.getCountry()
        );
        return hotelRepository.save(hotel);
    }
}
