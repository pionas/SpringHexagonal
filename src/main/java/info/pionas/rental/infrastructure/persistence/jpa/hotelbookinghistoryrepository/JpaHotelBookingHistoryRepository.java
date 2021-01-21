package info.pionas.rental.infrastructure.persistence.jpa.hotelbookinghistoryrepository;

import info.pionas.rental.domain.hotelbookinghistory.HotelBookingHistory;
import info.pionas.rental.domain.hotelbookinghistory.HotelBookingHistoryRepository;
import lombok.RequiredArgsConstructor;

/**
 * @author Adi
 */
@RequiredArgsConstructor
public class JpaHotelBookingHistoryRepository implements HotelBookingHistoryRepository {

    private final SpringJpaHotelBookingHistoryRepository springJpaHotelBookingHistoryRepository;

    @Override
    public HotelBookingHistory findFor(String apartmentId) {
//        return springJpaHotelBookingHistoryRepository.findById(apartmentId).get();
        return null;
    }

    @Override
    public boolean existFor(String apartmentId) {
//        return springJpaHotelBookingHistoryRepository.existsById(apartmentId);
        return false;
    }

    @Override
    public void save(HotelBookingHistory hotelBookingHistory) {
//        springJpaHotelBookingHistoryRepository.save(hotelBookingHistory);
    }

}
