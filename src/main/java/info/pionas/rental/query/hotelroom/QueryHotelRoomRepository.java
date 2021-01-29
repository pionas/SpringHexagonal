package info.pionas.rental.query.hotelroom;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
@RequiredArgsConstructor
public class QueryHotelRoomRepository {

    private final SpringJpaQueryHotelRoomRepository springJpaQueryHotelRoomRepository;

    public Iterable<HotelRoomReadModel> findAll(String hotelId) {
        return springJpaQueryHotelRoomRepository.findAllByHotelId(UUID.fromString(hotelId));
    }
}
