package info.pionas.rental.domain.apartment;

/**
 * @author Adi
 */
public interface BookingRepository {

    void save(Booking booking);

    Booking findById(String id);
}
