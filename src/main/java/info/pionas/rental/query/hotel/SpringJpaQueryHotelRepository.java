package info.pionas.rental.query.hotel;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Adi
 */
@Repository
interface SpringJpaQueryHotelRepository extends CrudRepository<HotelReadModel, String> {

}
