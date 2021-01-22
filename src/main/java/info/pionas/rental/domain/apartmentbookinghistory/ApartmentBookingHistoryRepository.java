package info.pionas.rental.domain.apartmentbookinghistory;

import org.springframework.stereotype.Repository;


@Repository
public interface ApartmentBookingHistoryRepository {

    ApartmentBookingHistory findFor(String apartmentId);

    boolean existsFor(String apartmentId);

    void save(ApartmentBookingHistory apartmentBookingHistory);

}
