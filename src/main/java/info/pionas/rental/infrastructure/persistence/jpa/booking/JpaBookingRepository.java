package info.pionas.rental.infrastructure.persistence.jpa.booking;

import info.pionas.rental.domain.apartment.Booking;
import info.pionas.rental.domain.apartment.BookingRepository;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author Adi
 */
@RequiredArgsConstructor
public class JpaBookingRepository implements BookingRepository {

    private final SpringJpaBookingRepository springJpaBookingRepository;

    @Override
    public void save(Booking booking) {
//        springJpaBookingRepository.save(booking);
    }

    @Override
    public Booking findById(String id) {
//        return springJpaBookingRepository.findById(id).get();
        return null;
    }

}
