package info.pionas.rental.application.booking;

import info.pionas.rental.domain.apartment.Booking;
import info.pionas.rental.domain.apartment.BookingAssertion;
import info.pionas.rental.domain.apartment.BookingRepository;
import info.pionas.rental.domain.apartment.Period;
import info.pionas.rental.domain.eventchannel.EventChannel;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

class BookingCommandHandlerTest {
    private static final String RENTAL_PLACE_ID = "123";
    private static final String TENANT_ID = "456";
    private static final String BOOKING_ID = "789";

    private final ArgumentCaptor<Booking> captor = ArgumentCaptor.forClass(Booking.class);
    private final BookingRepository bookingRepository = Mockito.mock(BookingRepository.class);
    private final EventChannel eventChannel = Mockito.mock(EventChannel.class);
    private final BookingCommandHandler handler = new BookingCommandHandler(bookingRepository, eventChannel);

    @Test
    void shouldRejectBooking() {
        givenExistingBooking();

        handler.reject(new BookingReject(BOOKING_ID));

        then(bookingRepository).should().save(captor.capture());
        BookingAssertion.assertThat(captor.getValue()).isReject();
    }

    @Test
    void shouldAcceptBooking() {
        givenExistingBooking();

        handler.accept(new BookingAccept(BOOKING_ID));

        then(bookingRepository).should().save(captor.capture());
        BookingAssertion.assertThat(captor.getValue()).isAccept();
    }

    private void givenExistingBooking() {
        Booking booking = getBooking();
        given(bookingRepository.findById(BOOKING_ID)).willReturn(booking);
    }

    private Booking getBooking() {
        LocalDate start = LocalDate.of(2020, 3, 4);
        LocalDate end = LocalDate.of(2020, 3, 6);
        return Booking.apartment(RENTAL_PLACE_ID, TENANT_ID, new Period(start, end));
    }
}