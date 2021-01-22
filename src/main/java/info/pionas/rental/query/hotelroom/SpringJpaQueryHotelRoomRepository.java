package info.pionas.rental.query.hotelroom;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
interface SpringJpaQueryHotelRoomRepository extends CrudRepository<HotelRoomReadModel, UUID> {

    public Iterable<HotelRoomReadModel> findAllByHotelId(String hotelId);

}
