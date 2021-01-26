package info.pionas.rental.domain.apartmentoffer;

import org.springframework.stereotype.Repository;

@Repository
public interface ApartmentOfferRepository {
    void save(ApartmentOffer apartmentOffer);

    ApartmentOffer findById(String id);
}
