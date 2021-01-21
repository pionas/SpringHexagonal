package info.pionas.rental.infrastructure.persistence.jpa.booking;

import info.pionas.rental.domain.apartment.Booking;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author Adi
 */
@Repository
interface SpringJpaBookingRepository extends CrudRepository<Booking, UUID> {

}
