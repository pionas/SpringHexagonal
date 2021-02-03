package info.pionas.rental.domain.booking;

import info.pionas.rental.domain.rentalplaceidentifier.RentalPlaceIdentifier;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BookingRepository {

    String save(Booking booking);

    Booking findById(String id);

    List<Booking> findAllBy(RentalPlaceIdentifier identifier);

    List<Booking> findAllAcceptedBy(RentalPlaceIdentifier identifier);
}
