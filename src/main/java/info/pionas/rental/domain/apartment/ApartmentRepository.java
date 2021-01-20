package info.pionas.rental.domain.apartment;

import org.springframework.stereotype.Repository;

/**
 *
 * @author Adi
 */
@Repository
public interface ApartmentRepository {

    void save(Apartment apartment);

    Apartment findById(String id);
}
