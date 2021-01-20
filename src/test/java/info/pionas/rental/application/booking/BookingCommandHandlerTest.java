package info.pionas.rental.application.booking;

import info.pionas.rental.domain.apartment.*;
import info.pionas.rental.domain.eventchannel.EventChannel;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.time.LocalDate;

import static java.util.Arrays.asList;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

class BookingCommandHandlerTest {
    private static final String RENTAL_PLACE_ID = "123";
    private static final String TENANT_ID = "456";
    private static final BookingRepository bookingRepository = Mockito.mock(BookingRepository.class);
    private static final EventChannel eventChannel = Mockito.mock(EventChannel.class);
    private static final BookingCommandHandler handler = new BookingCommandHandler(bookingRepository, eventChannel);
    public static final String BOOKING_ID = "123";

    @Test
    void shouldRejectBooking() {
        ArgumentCaptor<Booking> captor = ArgumentCaptor.forClass(Booking.class);
        BookingReject bookingReject = new BookingReject(BOOKING_ID);
        givenExistingBooking();

        handler.reject(bookingReject);

        then(bookingRepository).should().save(captor.capture());
        BookingAssertion.assertThat(captor.getValue())
                .isApartment()
                .isReject()
                .hasRentalPlaceIdEqualTo(RENTAL_PLACE_ID)
                .hasTenantIdEqualTo(TENANT_ID)
                .containsAllDays(
                        asList(
                                LocalDate.of(2020, 3, 4),
                                LocalDate.of(2020, 3, 5),
                                LocalDate.of(2020, 3, 6)
                        )
                );
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