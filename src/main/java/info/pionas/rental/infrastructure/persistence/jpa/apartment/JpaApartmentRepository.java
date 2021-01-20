package info.pionas.rental.infrastructure.persistence.jpa.apartment;

import info.pionas.rental.domain.apartment.Apartment;
import info.pionas.rental.domain.apartment.ApartmentRepository;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author Adi
 */
@RequiredArgsConstructor
class JpaApartmentRepository implements ApartmentRepository {

    private final SpringJpaApartmentRepository springJpaApartmentRepository;

    @Override
    public void save(Apartment apartment) {
//        springJpaApartmentRepository.save(apartment);
    }

    @Override
    public Apartment findById(String id) {
//        return springJpaApartmentRepository.findById(id).get();
        return null;
    }
}
