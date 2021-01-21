package info.pionas.rental.infrastructure.persistence.jpa.hotelroom;

import info.pionas.rental.domain.hotelroom.HotelRoom;
import info.pionas.rental.domain.hotelroom.HotelRoomRepository;
import lombok.RequiredArgsConstructor;

/**
 * @author Adi
 */
@RequiredArgsConstructor
public class JpaHotelRoomRepository implements HotelRoomRepository {

    private final SpringJpaHotelRoomRepository springJpaHotelRoomRepository;

    @Override
    public void save(HotelRoom hotelRoom) {
//        springJpaHotelRoomRepository.save(hotelRoom);
    }

    @Override
    public HotelRoom findById(String id) {
//        return springJpaHotelRoomRepository.findById(id).get();
        return null;
    }
}
