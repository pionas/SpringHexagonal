package info.pionas.rental.infrastructure.persistence.jpa.hotelroom;

import info.pionas.rental.domain.hotelroom.HotelRoom;
import info.pionas.rental.domain.hotelroom.HotelRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author Adi
 */
@Repository
@RequiredArgsConstructor
class JpaHotelRoomRepository implements HotelRoomRepository {

    private final SpringJpaHotelRoomRepository springJpaHotelRoomRepository;

    @Override
    public String save(HotelRoom hotelRoom) {
        return springJpaHotelRoomRepository.save(hotelRoom).id();
    }

    @Override
    public HotelRoom findById(String id) {
        return springJpaHotelRoomRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new HotelRoomDoesNotExistException(id));
    }
}