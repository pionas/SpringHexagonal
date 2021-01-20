package info.pionas.rental.infrastructure.persistence.jpa.apartment;

import info.pionas.rental.domain.apartment.Apartment;
import info.pionas.rental.domain.apartment.ApartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Adi
 */
@Repository
@RequiredArgsConstructor
class JpaApartmentRepository implements ApartmentRepository {

    private final SpringJpaApartmentRepository springJpaApartmentRepository;

    @Override
    public void save(Apartment apartment) {
//        springJpaApartmentRepository.save(apartment);
    }

    @Override
    public Apartment findById(String id) {
//        return springJpaApartmentRepository.findById(id).orElse(new ApartmentDoesNotExistException(id));
        throw new ApartmentDoesNotExistException(id);
    }
}
