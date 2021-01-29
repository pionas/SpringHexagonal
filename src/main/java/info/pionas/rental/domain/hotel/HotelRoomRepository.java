package info.pionas.rental.domain.hotel;

import org.springframework.stereotype.Repository;


@Repository
public interface HotelRoomRepository {

    String save(HotelRoom hotelRoom);

    HotelRoom findById(String id);

    boolean existById(String hotelRoomId);
}
