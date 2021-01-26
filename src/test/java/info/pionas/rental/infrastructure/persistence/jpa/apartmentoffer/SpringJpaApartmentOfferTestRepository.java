package info.pionas.rental.infrastructure.persistence.jpa.apartmentoffer;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Repository
public class SpringJpaApartmentOfferTestRepository {
    private final SpringJpaApartmentOfferRepository repository;

    public void deleteById(String apartmentOfferId) {
        repository.deleteById(UUID.fromString(apartmentOfferId));
    }

    public void deleteAll(List<String> apartmentOfferIds) {
        apartmentOfferIds.forEach(this::deleteById);
    }
}