package info.pionas.rental.query.hotelroom;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * @author Adi
 */
@Repository
@RequiredArgsConstructor
public class QueryHotelRoomRepository {

    private final SpringJpaQueryHotelRoomRepository springJpaQueryHotelRoomRepository;

    public Iterable<HotelRoomReadModel> findAll(String hotelId) {
        return springJpaQueryHotelRoomRepository.findAllByHotelId(hotelId);
    }
}
