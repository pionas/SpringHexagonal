package info.pionas.rental.infrastructure.persistence.jpa.apartmentoffer;

import info.pionas.rental.domain.apartmentoffer.ApartmentOffer;
import info.pionas.rental.domain.apartmentoffer.ApartmentOfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaApartmentOfferRepository implements ApartmentOfferRepository {

    @Override
    public void save(ApartmentOffer apartmentOffer) {

    }

    @Override
    public ApartmentOffer findById(String id) {
        return null;
    }
}
