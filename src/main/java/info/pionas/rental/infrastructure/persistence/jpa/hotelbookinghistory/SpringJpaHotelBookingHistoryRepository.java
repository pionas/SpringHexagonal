package info.pionas.rental.infrastructure.persistence.jpa.hotelbookinghistory;

import info.pionas.rental.domain.hotelbookinghistory.HotelBookingHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Adi
 */
@Repository
interface SpringJpaHotelBookingHistoryRepository extends CrudRepository<HotelBookingHistory, String> {
}
