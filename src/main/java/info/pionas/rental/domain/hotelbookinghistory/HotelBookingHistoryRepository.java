package info.pionas.rental.domain.hotelbookinghistory;

import org.springframework.stereotype.Repository;

/**
 * @author Adi
 */
@Repository
public interface HotelBookingHistoryRepository {

    HotelBookingHistory findFor(String hotelId);

    boolean existFor(String hotelId);

    void save(HotelBookingHistory hotelBookingHistory);
}
