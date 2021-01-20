package info.pionas.rental.infrastructure.persistence.jpa.apartment;

import info.pionas.rental.domain.apartment.Apartment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Adi
 */
@Repository
interface SpringJpaApartmentRepository extends CrudRepository<Apartment, String> {
}
