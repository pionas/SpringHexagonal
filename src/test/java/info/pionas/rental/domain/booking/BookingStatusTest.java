package info.pionas.rental.domain.booking;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static info.pionas.rental.domain.booking.BookingStatus.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BookingStatusTest {
    @ParameterizedTest
    @EnumSource(names = {"ACCEPTED", "OPEN"}, value = BookingStatus.class)
    void shouldNotAllowForTransitionFromRejected(BookingStatus bookingStatus) {
        NotAllowedBookingStatusTransitionException actual = assertThrows(NotAllowedBookingStatusTransitionException.class, () -> REJECTED.moveTo(bookingStatus));

        assertThat(actual).hasMessage("Not allowed transition from REJECTED to " + bookingStatus + " booking");
    }

    @ParameterizedTest
    @EnumSource(names = {"REJECTED", "OPEN"}, value = BookingStatus.class)
    void shouldNotAllowForTransitionFromAccepted(BookingStatus bookingStatus) {
        NotAllowedBookingStatusTransitionException actual = assertThrows(NotAllowedBookingStatusTransitionException.class, () -> ACCEPTED.moveTo(bookingStatus));

        assertThat(actual).hasMessage("Not allowed transition from ACCEPTED to " + bookingStatus + " booking");
    }

    @ParameterizedTest
    @EnumSource(names = {"REJECTED", "ACCEPTED"}, value = BookingStatus.class)
    void shouldAllowForTransitionFromOpen(BookingStatus bookingStatus) {
        BookingStatus actual = OPEN.moveTo(bookingStatus);

        assertThat(actual).isEqualTo(bookingStatus);
    }
}