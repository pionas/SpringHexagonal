package info.pionas.rental.infrastructure.persistence.jpa.hotel;

import info.pionas.rental.domain.hotel.Hotel;
import info.pionas.rental.domain.hotel.HotelRepository;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author Adi
 */
@RequiredArgsConstructor
public class JpaHotelRepository implements HotelRepository {

    private final SpringJpaHotelRepository springJpaHotelRepository;

    @Override
    public void save(Hotel hotel) {
//        springJpaHotelRepository.save(hotel);
    }
}
