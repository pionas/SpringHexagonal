package info.pionas.rental.query.apartment;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author Adi
 */
@Repository
interface SpringQueryApartmentRepository extends CrudRepository<ApartmentReadModel, UUID> {

}
