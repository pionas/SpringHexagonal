package info.pionas.rental.domain.apartment;

import org.springframework.stereotype.Repository;


@Repository
public interface BookingRepository {

    String save(Booking booking);

    Booking findById(String id);
}
