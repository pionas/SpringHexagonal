package info.pionas.rental.domain.apartmentbookinghistory;

import org.springframework.stereotype.Repository;

/**
 * @author Adi
 */
@Repository
public interface ApartmentBookingHistoryRepository {

    ApartmentBookingHistory findFor(String apartmentId);

    boolean existFor(String apartmentId);

    void save(ApartmentBookingHistory apartmentBookingHistory);

}
