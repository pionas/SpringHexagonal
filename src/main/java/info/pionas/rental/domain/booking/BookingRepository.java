package info.pionas.rental.domain.booking;

import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BookingRepository {

    String save(Booking booking);

    Booking findById(String id);

    List<Booking> findAllBy(RentalPlaceIdentifier identifier);
}
