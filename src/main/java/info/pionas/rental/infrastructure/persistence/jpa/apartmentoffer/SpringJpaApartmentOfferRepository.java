package info.pionas.rental.infrastructure.persistence.jpa.apartmentoffer;

import info.pionas.rental.domain.apartmentoffer.ApartmentOffer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
interface SpringJpaApartmentOfferRepository extends CrudRepository<ApartmentOffer, UUID> {
}