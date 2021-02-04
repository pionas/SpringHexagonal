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

    public void deleteById(UUID apartmentOfferId) {
        repository.deleteById(apartmentOfferId);
    }

    public void deleteAll(List<String> apartmentOfferIds) {
        apartmentOfferIds.forEach(apartmentOfferId -> deleteById(UUID.fromString(apartmentOfferId)));
    }
}