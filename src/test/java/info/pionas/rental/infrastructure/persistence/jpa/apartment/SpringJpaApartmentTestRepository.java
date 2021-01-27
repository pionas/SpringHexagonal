package info.pionas.rental.infrastructure.persistence.jpa.apartment;

import info.pionas.rental.domain.apartment.Apartment;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Repository
public class SpringJpaApartmentTestRepository {
    private final SpringJpaApartmentRepository repository;

    public void deleteById(String apartmentId) {
        repository.deleteById(UUID.fromString(apartmentId));
    }

    public void deleteAll(List<String> apartmentIds) {
        apartmentIds.forEach(this::deleteById);
    }

    public String save(Apartment apartment) {
        return repository.save(apartment).id();
    }
}