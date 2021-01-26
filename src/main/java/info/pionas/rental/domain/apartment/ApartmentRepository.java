package info.pionas.rental.domain.apartment;

import org.springframework.stereotype.Repository;


@Repository
public interface ApartmentRepository {

    String save(Apartment apartment);

    Apartment findById(String id);

    boolean existById(String apartmentId);
}
