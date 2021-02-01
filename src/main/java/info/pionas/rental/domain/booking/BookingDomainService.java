package info.pionas.rental.domain.booking;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class BookingDomainService {
    private final BookingEventsPublisher bookingEventsPublisher;

    public void accept(Booking booking, List<Booking> bookings) {
        if (hasNoCollisions(booking, bookings)) {
            booking.accept(bookingEventsPublisher);
        } else {
            booking.reject(bookingEventsPublisher);
        }

    }

    private boolean hasNoCollisions(Booking bookingToAccept, List<Booking> bookings) {
        return bookings.stream().noneMatch(booking -> booking.hasCollisionWith(bookingToAccept));
    }
}
