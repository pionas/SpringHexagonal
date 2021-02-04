package info.pionas.rental.infrastructure.persistence.jpa.apartmentoffer;

import info.pionas.rental.domain.apartmentoffer.ApartmentOffer;
import info.pionas.rental.domain.apartmentoffer.ApartmentOfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class JpaApartmentOfferRepository implements ApartmentOfferRepository {
    private final SpringJpaApartmentOfferRepository springJpaApartmentOfferRepository;

    @Override
    public UUID save(ApartmentOffer apartmentOffer) {
        return springJpaApartmentOfferRepository.save(apartmentOffer).id();
    }

    @Override
    public boolean existByApartmentId(String apartmentId) {
        return springJpaApartmentOfferRepository.existsByApartmentId(apartmentId);
    }

    @Override
    public ApartmentOffer findByApartmentId(String apartmentId) {
        return springJpaApartmentOfferRepository.findByApartmentId(apartmentId);
    }
}
