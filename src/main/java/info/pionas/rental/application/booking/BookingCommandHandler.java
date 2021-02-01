package info.pionas.rental.application.booking;

import info.pionas.rental.domain.booking.Booking;
import info.pionas.rental.domain.booking.BookingDomainService;
import info.pionas.rental.domain.booking.BookingEventsPublisher;
import info.pionas.rental.domain.booking.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;

import java.util.List;

@RequiredArgsConstructor
public class BookingCommandHandler {

    private final BookingRepository bookingRepository;
    private final BookingDomainService bookingDomainService;
    private final BookingEventsPublisher bookingEventsPublisher;

    @EventListener
    public void reject(BookingReject bookingReject) {
        Booking booking = bookingRepository.findById(bookingReject.getBookingId());
        booking.reject(bookingEventsPublisher);

        bookingRepository.save(booking);
    }

    @EventListener
    public void accept(BookingAccept bookingAccept) {
        Booking booking = bookingRepository.findById(bookingAccept.getBookingId());
        List<Booking> bookings = bookingRepository.findAllBy(booking.rentalPlaceIdentifier());

        bookingDomainService.accept(booking, bookings);
        bookingRepository.save(booking);
    }
}
