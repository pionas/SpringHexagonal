package info.pionas.rental.domain.apartmentoffer;

import org.springframework.stereotype.Repository;

@Repository
public interface ApartmentOfferRepository {
    String save(ApartmentOffer apartmentOffer);

    ApartmentOffer findById(String id);
}
