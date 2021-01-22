package info.pionas.rental.infrastructure.persistence.jpa.apartmentbookinghistory;

import info.pionas.rental.domain.apartmentbookinghistory.ApartmentBookingHistory;
import info.pionas.rental.domain.apartmentbookinghistory.ApartmentBookingHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class JpaApartmentBookingHistoryRepository implements ApartmentBookingHistoryRepository {

    private final SpringJpaApartmentBookingHistoryRepository springJpaApartmentBookingHistoryRepository;

    @Override
    public ApartmentBookingHistory findFor(String apartmentId) {
        return springJpaApartmentBookingHistoryRepository.findById(apartmentId).get();
    }

    @Override
    public boolean existsFor(String apartmentId) {
        return springJpaApartmentBookingHistoryRepository.existsById(apartmentId);
    }

    @Override
    public void save(ApartmentBookingHistory apartmentBookingHistory) {
        springJpaApartmentBookingHistoryRepository.save(apartmentBookingHistory);
    }

}
