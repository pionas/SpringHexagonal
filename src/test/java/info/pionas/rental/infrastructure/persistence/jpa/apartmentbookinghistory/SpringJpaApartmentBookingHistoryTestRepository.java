package info.pionas.rental.infrastructure.persistence.jpa.apartmentbookinghistory;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Repository
public class SpringJpaApartmentBookingHistoryTestRepository {
    private final SpringJpaApartmentBookingHistoryRepository repository;

    public void deleteById(String apartmentId) {
        repository.deleteById(apartmentId);
    }

    public void deleteAll(List<String> apartmentBookingHistoryIds) {
        apartmentBookingHistoryIds.forEach(this::deleteById);
    }

}
