package info.pionas.rental.query.apartment;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
interface SpringQueryApartmentBookingHistoryRepository extends CrudRepository<ApartmentBookingHistoryReadModel, String> {

}
