package info.pionas.rental.infrastructure.persistence.jpa.hotel;

import info.pionas.rental.domain.hotel.Hotel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
interface SpringJpaHotelRepository extends CrudRepository<Hotel, UUID> {
}
