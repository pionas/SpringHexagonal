package info.pionas.rental.infrastructure.persistence.jpa.apartment;

import info.pionas.rental.domain.apartment.Apartment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author Adi
 */
@Repository
interface SpringJpaApartmentRepository extends CrudRepository<Apartment, UUID> {
}
