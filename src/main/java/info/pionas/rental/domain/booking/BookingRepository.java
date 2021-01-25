package info.pionas.rental.domain.booking;

import org.springframework.stereotype.Repository;


@Repository
public interface BookingRepository {

    String save(Booking booking);

    Booking findById(String id);
}
