package info.pionas.rental.infrastructure.persistence.jpa.booking;

import info.pionas.rental.domain.booking.Booking;
import info.pionas.rental.domain.booking.BookingRepository;
import info.pionas.rental.domain.booking.RentalPlaceIdentifier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.UUID;


@Repository
@RequiredArgsConstructor
public class JpaBookingRepository implements BookingRepository {

    private final SpringJpaBookingRepository springJpaBookingRepository;

    @Override
    public String save(Booking booking) {
        return springJpaBookingRepository.save(booking).id();
    }

    @Override
    public Booking findById(String id) {
        return springJpaBookingRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new BookingDoesNotExistException(id));
    }

    @Override
    public List<Booking> findAllBy(RentalPlaceIdentifier identifier) {
        return springJpaBookingRepository.findAllByRentalTypeAndRentalPlaceId(identifier.getRentalType(), identifier.getRentalPlaceId());
    }

    @Override
    public List<Booking> findAllAcceptedBy(RentalPlaceIdentifier identifier) {
        return Collections.emptyList();
    }

}
