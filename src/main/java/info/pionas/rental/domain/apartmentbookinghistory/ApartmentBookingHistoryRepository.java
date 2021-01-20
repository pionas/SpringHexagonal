package info.pionas.rental.domain.apartmentbookinghistory;

/**
 *
 * @author Adi
 */
public interface ApartmentBookingHistoryRepository {

    ApartmentBookingHistory findFor(String apartmentId);

    boolean existFor(String apartmentId);

    void save(ApartmentBookingHistory apartmentBookingHistory);

}
