package info.pionas.rental.domain.hotelbookinghistory;

/**
 *
 * @author Adi
 */
public interface HotelBookingHistoryRepository {

    HotelBookingHistory findFor(String hotelId);

    boolean existFor(String hotelId);

    void save(HotelBookingHistory hotelBookingHistory);
}
