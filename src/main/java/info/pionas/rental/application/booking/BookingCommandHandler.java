package info.pionas.rental.application.booking;

import info.pionas.rental.domain.apartment.Booking;
import info.pionas.rental.domain.apartment.BookingRepository;
import info.pionas.rental.domain.eventchannel.EventChannel;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author Adi
 */
@Component
@RequiredArgsConstructor
public class BookingCommandHandler {

    private final BookingRepository bookingRepository;
    private final EventChannel eventChannel;

    @EventListener
    public void reject(BookingReject bookingReject) {
        Booking booking = bookingRepository.findById(bookingReject.getBookingId());
        booking.reject();

        bookingRepository.save(booking);

    }

    @EventListener
    public void accept(BookingAccept bookingAccept) {
        Booking booking = bookingRepository.findById(bookingAccept.getBookingId());
        booking.accept(eventChannel);

        bookingRepository.save(booking);

    }
}
