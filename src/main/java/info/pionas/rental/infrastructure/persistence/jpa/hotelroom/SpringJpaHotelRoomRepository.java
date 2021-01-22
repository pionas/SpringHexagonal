package info.pionas.rental.infrastructure.persistence.jpa.hotelroom;

import info.pionas.rental.domain.hotelroom.HotelRoom;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
interface SpringJpaHotelRoomRepository extends CrudRepository<HotelRoom, UUID> {
}
