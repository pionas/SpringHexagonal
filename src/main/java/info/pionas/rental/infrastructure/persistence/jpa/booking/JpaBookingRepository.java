package info.pionas.rental.infrastructure.persistence.jpa.booking;

import info.pionas.rental.domain.apartment.Booking;
import info.pionas.rental.domain.apartment.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author Adi
 */
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

}
