package info.pionas.rental.infrastructure.persistence.jpa.apartment;

import info.pionas.rental.domain.apartment.Apartment;
import info.pionas.rental.domain.apartment.ApartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
@RequiredArgsConstructor
class JpaApartmentRepository implements ApartmentRepository {

    private final SpringJpaApartmentRepository springJpaApartmentRepository;

    @Override
    public String save(Apartment apartment) {
        return springJpaApartmentRepository.save(apartment).id();
    }

    @Override
    public Apartment findById(String id) {
        return springJpaApartmentRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new ApartmentDoesNotExistException(id));
    }

    @Override
    public boolean existById(String apartmentId) {
        return springJpaApartmentRepository.existsById(UUID.fromString(apartmentId));
    }
}
