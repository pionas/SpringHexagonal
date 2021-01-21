package info.pionas.rental.infrastructure.persistence.jpa.apartmentbookinghistory;

import info.pionas.rental.domain.apartmentbookinghistory.ApartmentBookingHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Adi
 */
@Repository
public interface SpringJpaApartmentBookingHistoryRepository extends CrudRepository<ApartmentBookingHistory, String> {
}
