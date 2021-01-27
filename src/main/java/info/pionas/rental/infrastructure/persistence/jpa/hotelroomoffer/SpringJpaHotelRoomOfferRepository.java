package info.pionas.rental.infrastructure.persistence.jpa.hotelroomoffer;

import info.pionas.rental.domain.hotelroomoffer.HotelRoomOffer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
interface SpringJpaHotelRoomOfferRepository extends CrudRepository<HotelRoomOffer, UUID> {
}
