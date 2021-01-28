package info.pionas.rental.application.booking;

import info.pionas.rental.domain.booking.Booking;
import info.pionas.rental.domain.booking.BookingAccepted;
import info.pionas.rental.domain.booking.BookingAssertion;
import info.pionas.rental.domain.booking.BookingRepository;
import info.pionas.rental.domain.eventchannel.EventChannel;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;

import java.time.LocalDate;
import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

class BookingCommandHandlerTest {
    private static final String BOOKING_ID = "74398";
    private static final String RENTAL_PLACE_ID = "1234";
    private static final String TENANT_ID = "5678";
    private static final List<LocalDate> DAYS = asList(LocalDate.now(), LocalDate.now().plusDays(1));

    private final ArgumentCaptor<Booking> captor = ArgumentCaptor.forClass(Booking.class);

    private final BookingRepository bookingRepository = mock(BookingRepository.class);
    private final EventChannel eventChannel = mock(EventChannel.class);
    private final BookingCommandHandler commandHandler = new BookingCommandHandlerFactory().bookingCommandHandler(bookingRepository, eventChannel);

    @Test
    void shouldAcceptBooking() {
        givenOpenBooking();

        commandHandler.accept(new BookingAccept(BOOKING_ID));

        then(bookingRepository).should().save(captor.capture());
        BookingAssertion.assertThat(captor.getValue()).isAccepted();
    }

    @Test
    void shouldPublishBookingAcceptedOnceAccepted() {
        ArgumentCaptor<BookingAccepted> captor = ArgumentCaptor.forClass(BookingAccepted.class);
        givenOpenBooking();

        commandHandler.accept(new BookingAccept(BOOKING_ID));

        BDDMockito.then(eventChannel).should().publish(captor.capture());
        BookingAccepted actual = captor.getValue();
        Assertions.assertThat(actual.getRentalType()).isEqualTo("HOTEL_ROOM");
        Assertions.assertThat(actual.getRentalPlaceId()).isEqualTo(RENTAL_PLACE_ID);
        Assertions.assertThat(actual.getTenantId()).isEqualTo(TENANT_ID);
        Assertions.assertThat(actual.getDays()).containsExactlyElementsOf(DAYS);
    }

    @Test
    void shouldRejectBooking() {
        givenOpenBooking();

        commandHandler.reject(new BookingReject(BOOKING_ID));

        then(bookingRepository).should().save(captor.capture());
        BookingAssertion.assertThat(captor.getValue()).isRejected();
    }

    private void givenOpenBooking() {
        Booking booking = Booking.hotelRoom(RENTAL_PLACE_ID, TENANT_ID, DAYS);
        given(bookingRepository.findById(BOOKING_ID)).willReturn(booking);
    }
}