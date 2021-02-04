package info.pionas.rental.domain.booking;

import info.pionas.rental.domain.agreement.Agreement;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class BookingDomainService {
    private final BookingEventsPublisher bookingEventsPublisher;

    public Optional<Agreement> accept(Booking booking, List<Booking> bookings) {
        if (hasNoCollisions(booking, bookings)) {
            booking.accept(bookingEventsPublisher);
        } else {
            booking.reject(bookingEventsPublisher);
        }
        return Optional.empty();
    }

    private boolean hasNoCollisions(Booking bookingToAccept, List<Booking> bookings) {
        return bookings == null || bookings.stream().noneMatch(booking -> booking.hasCollisionWith(bookingToAccept));
    }
}
