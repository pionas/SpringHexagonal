package info.pionas.rental.domain.apartment;

/**
 * @author Adi
 */
public interface BookingRepository {

    String save(Booking booking);

    Booking findById(String id);
}
