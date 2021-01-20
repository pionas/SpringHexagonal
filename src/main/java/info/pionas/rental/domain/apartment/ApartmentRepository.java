package info.pionas.rental.domain.apartment;

/**
 *
 * @author Adi
 */
public interface ApartmentRepository {

    void save(Apartment apartment);

    Apartment findById(String id);
}
