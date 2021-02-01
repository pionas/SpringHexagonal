package info.pionas.rental.domain.booking;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class BookingDomainService {
    private final BookingEventsPublisher bookingEventsPublisher;

    public void accept(Booking booking, List<Object> bookings) {
        booking.accept(bookingEventsPublisher);
    }
}
