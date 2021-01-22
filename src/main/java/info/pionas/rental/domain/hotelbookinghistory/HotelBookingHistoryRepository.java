package info.pionas.rental.domain.hotelbookinghistory;

import org.springframework.stereotype.Repository;


@Repository
public interface HotelBookingHistoryRepository {

    HotelBookingHistory findFor(String hotelId);

    boolean existsFor(String hotelId);

    void save(HotelBookingHistory hotelBookingHistory);
}
