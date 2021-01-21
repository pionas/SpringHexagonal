package info.pionas.rental.domain.apartment;

import org.springframework.stereotype.Repository;

/**
 * @author Adi
 */
@Repository
public interface BookingRepository {

    String save(Booking booking);

    Booking findById(String id);
}
