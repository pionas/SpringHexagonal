package info.pionas.rental.infrastructure.persistence.jpa.booking;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Repository
public class SpringJpaBookingTestRepository {
    private final SpringJpaBookingRepository repository;

    public void deleteById(String apartmentId) {
        repository.deleteById(UUID.fromString(apartmentId));
    }

}