package info.pionas.rental.infrastructure.persistence.jpa.hotelbookinghistory;

import info.pionas.rental.domain.hotelbookinghistory.HotelBookingHistory;
import info.pionas.rental.domain.hotelbookinghistory.HotelBookingHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * @author Adi
 */
@Repository
@RequiredArgsConstructor
public class JpaHotelBookingHistoryRepository implements HotelBookingHistoryRepository {

    private final SpringJpaHotelBookingHistoryRepository springJpaHotelBookingHistoryRepository;

    @Override
    public HotelBookingHistory findFor(String apartmentId) {
        return springJpaHotelBookingHistoryRepository.findById(apartmentId).get();
    }

    @Override
    public boolean existFor(String apartmentId) {
        return springJpaHotelBookingHistoryRepository.existsById(apartmentId);
    }

    @Override
    public void save(HotelBookingHistory hotelBookingHistory) {
        springJpaHotelBookingHistoryRepository.save(hotelBookingHistory);
    }

}
