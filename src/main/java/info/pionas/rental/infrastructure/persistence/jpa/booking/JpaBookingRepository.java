package info.pionas.rental.infrastructure.persistence.jpa.booking;

import info.pionas.rental.domain.booking.Booking;
import info.pionas.rental.domain.booking.BookingRepository;
import info.pionas.rental.domain.booking.BookingStatus;
import info.pionas.rental.domain.rentalplaceidentifier.RentalPlaceIdentifier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
class JpaBookingRepository implements BookingRepository {
    private final SpringJpaBookingRepository springJpaBookingRepository;

    @Override
    public UUID save(Booking booking) {
        return springJpaBookingRepository.save(booking).id();
    }

    @Override
    public Booking findById(String bookingId) {
        return springJpaBookingRepository.findById(UUID.fromString(bookingId)).get();
    }

    @Override
    public List<Booking> findAllBy(RentalPlaceIdentifier identifier) {
        return springJpaBookingRepository.findAllByRentalTypeAndRentalPlaceId(identifier.getRentalType(), identifier.getRentalPlaceId());
    }

    @Override
    public List<Booking> findAllAcceptedBy(RentalPlaceIdentifier identifier) {
        return springJpaBookingRepository.findAllByRentalTypeAndRentalPlaceIdAndBookingStatus(identifier.getRentalType(), identifier.getRentalPlaceId(), BookingStatus.ACCEPTED);
    }
}
