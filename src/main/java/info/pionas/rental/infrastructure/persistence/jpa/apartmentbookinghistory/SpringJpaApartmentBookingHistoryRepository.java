package info.pionas.rental.infrastructure.persistence.jpa.apartmentbookinghistory;

import info.pionas.rental.domain.apartmentbookinghistory.ApartmentBookingHistory;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Adi
 */
public interface SpringJpaApartmentBookingHistoryRepository extends CrudRepository<ApartmentBookingHistory, String> {
}
