package info.pionas.rental.domain.hotelroom;

import org.springframework.stereotype.Repository;


@Repository
public interface HotelRoomRepository {

    String save(HotelRoom hotelRoom);

    HotelRoom findById(String id);
}
