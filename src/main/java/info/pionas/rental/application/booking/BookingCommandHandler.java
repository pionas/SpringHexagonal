package info.pionas.rental.application.booking;

import info.pionas.rental.domain.booking.Booking;
import info.pionas.rental.domain.booking.BookingEventsPublisher;
import info.pionas.rental.domain.booking.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;

@RequiredArgsConstructor
public class BookingCommandHandler {

    private final BookingRepository bookingRepository;
    private final BookingEventsPublisher bookingEventsPublisher;

    @EventListener
    public void reject(BookingReject bookingReject) {
        Booking booking = bookingRepository.findById(bookingReject.getBookingId());
        booking.reject();

        bookingRepository.save(booking);

    }

    @EventListener
    public void accept(BookingAccept bookingAccept) {
        Booking booking = bookingRepository.findById(bookingAccept.getBookingId());
        booking.accept(bookingEventsPublisher);

        bookingRepository.save(booking);

    }
}
