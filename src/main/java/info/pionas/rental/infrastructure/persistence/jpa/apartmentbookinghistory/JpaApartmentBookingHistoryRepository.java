package info.pionas.rental.infrastructure.persistence.jpa.apartmentbookinghistory;

import info.pionas.rental.domain.apartmentbookinghistory.ApartmentBookingHistory;
import info.pionas.rental.domain.apartmentbookinghistory.ApartmentBookingHistoryRepository;
import lombok.RequiredArgsConstructor;

/**
 * @author Adi
 */
@RequiredArgsConstructor
public class JpaApartmentBookingHistoryRepository implements ApartmentBookingHistoryRepository {

    private final SpringJpaApartmentBookingHistoryRepository springJpaApartmentBookingHistoryRepository;

    @Override
    public ApartmentBookingHistory findFor(String apartmentId) {
//        return springJpaApartmentBookingHistoryRepository.findById(apartmentId).get();
        return null;
    }

    @Override
    public boolean existFor(String apartmentId) {
//        return springJpaApartmentBookingHistoryRepository.existsById(apartmentId);
        return false;
    }

    @Override
    public void save(ApartmentBookingHistory apartmentBookingHistory) {
//        springJpaApartmentBookingHistoryRepository.save(apartmentBookingHistory);
    }

}
