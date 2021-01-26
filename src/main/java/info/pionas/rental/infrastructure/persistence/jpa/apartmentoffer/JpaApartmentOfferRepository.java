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
    public String save(ApartmentOffer apartmentOffer) {

        return null;
    }

    @Override
    public ApartmentOffer findById(String id) {
        return springJpaApartmentOfferRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new ApartmentOfferDoesNotExistException(id));
    }
}
