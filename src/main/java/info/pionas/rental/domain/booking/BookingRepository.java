package info.pionas.rental.domain.booking;

import info.pionas.rental.domain.rentalplaceidentifier.RentalPlaceIdentifier;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface BookingRepository {

    UUID save(Booking booking);

    Booking findById(String id);

    List<Booking> findAllBy(RentalPlaceIdentifier identifier);

    List<Booking> findAllAcceptedBy(RentalPlaceIdentifier identifier);
}
