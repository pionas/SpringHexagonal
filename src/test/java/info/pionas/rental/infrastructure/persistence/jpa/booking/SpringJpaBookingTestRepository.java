package info.pionas.rental.infrastructure.persistence.jpa.booking;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Repository
public class SpringJpaBookingTestRepository {
    private final SpringJpaBookingRepository repository;

    public void deleteById(UUID apartmentId) {
        repository.deleteById(apartmentId);
    }

    public void deleteAll(List<String> bookingIds) {
        bookingIds.forEach(bookingId -> deleteById(UUID.fromString(bookingId)));
    }
}