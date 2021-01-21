package info.pionas.rental.infrastructure.persistence.jpa.hotel;

import info.pionas.rental.domain.hotel.Hotel;
import info.pionas.rental.domain.hotel.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author Adi
 */
@Repository
@RequiredArgsConstructor
public class JpaHotelRepository implements HotelRepository {

    private final SpringJpaHotelRepository springJpaHotelRepository;


    @Override
    public String save(Hotel hotel) {
        return springJpaHotelRepository.save(hotel).id();
    }

    @Override
    public Hotel findById(String id) {
        return springJpaHotelRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new HotelDoesNotExistException(id));
    }
}
