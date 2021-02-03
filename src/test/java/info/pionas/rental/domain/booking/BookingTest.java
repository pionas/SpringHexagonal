package info.pionas.rental.domain.booking;

import info.pionas.rental.domain.period.Period;
import info.pionas.rental.domain.rentalplaceidentifier.RentalType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static info.pionas.rental.domain.booking.BookingAssertion.assertThat;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

class BookingTest {
    private static final List<LocalDate> DAYS = asList(LocalDate.now(), LocalDate.now().plusDays(1));
    private static final String TENANT_ID = "1234";
    private static final String RENTAL_PLACE_ID = "5748";

    private final BookingEventsPublisher bookingEventsPublisher = mock(BookingEventsPublisher.class);

    @Test
    void shouldCreateBookingForApartment() {
        Period period = new Period(LocalDate.of(2020, 3, 4), LocalDate.of(2020, 3, 6));

        Booking actual = Booking.apartment(RENTAL_PLACE_ID, TENANT_ID, period);

        assertThat(actual)
                .isOpen()
                .isApartment()
                .hasRentalPlaceIdEqualTo(RENTAL_PLACE_ID)
                .hasTenantIdEqualTo(TENANT_ID)
                .containsAllDays(LocalDate.of(2020, 3, 4), LocalDate.of(2020, 3, 5), LocalDate.of(2020, 3, 6));
    }

    @Test
    void shouldCreateBookingForHotelRoom() {
        List<LocalDate> days = asList(LocalDate.of(2020, 6, 1), LocalDate.of(2020, 6, 2), LocalDate.of(2020, 6, 4));

        Booking actual = givenBooking(days);

        assertThat(actual)
                .isOpen()
                .isHotelRoom()
                .hasRentalPlaceIdEqualTo(RENTAL_PLACE_ID)
                .hasTenantIdEqualTo(TENANT_ID)
                .containsAllDays(days);
    }

    @Test
    void shouldChangeStatusOfBookingOnceAccepted() {
        Booking booking = givenBooking(DAYS);

        booking.accept(bookingEventsPublisher);

        assertThat(booking).isAccepted();
    }

    @Test
    void shouldPublishBookingAcceptedOnceAccepted() {
        Booking booking = givenBooking(DAYS);

        booking.accept(bookingEventsPublisher);

        then(bookingEventsPublisher).should().bookingAccepted(RentalType.HOTEL_ROOM, RENTAL_PLACE_ID, TENANT_ID, DAYS);
    }

    @Test
    void shouldChangeStatusOfBookingOnceRejected() {
        Booking booking = givenBooking(DAYS);

        booking.reject(bookingEventsPublisher);

        assertThat(booking).isRejected();
    }

    @Test
    void shouldNotAllowedToRejectAlreadyAcceptedBooking() {
        Booking booking = givenBooking(DAYS);
        booking.accept(bookingEventsPublisher);

        NotAllowedBookingStatusTransitionException actual = assertThrows(NotAllowedBookingStatusTransitionException.class, () -> {
            booking.reject(bookingEventsPublisher);
        });
        Assertions.assertThat(actual).hasMessage("Not allowed transition from ACCEPTED to REJECTED booking");
        assertThat(booking).isAccepted();
    }


    @Test
    void shouldNotAllowedToAcceptAlreadyRejectBooking() {
        Booking booking = givenBooking(DAYS);
        booking.reject(bookingEventsPublisher);

        NotAllowedBookingStatusTransitionException actual = assertThrows(NotAllowedBookingStatusTransitionException.class, () -> {
            booking.accept(bookingEventsPublisher);
        });

        Assertions.assertThat(actual).hasMessage("Not allowed transition from REJECTED to ACCEPTED booking");
        assertThat(booking).isRejected();
    }

    private Booking givenBooking(List<LocalDate> days) {
        return Booking.hotelRoom(RENTAL_PLACE_ID, TENANT_ID, days);
    }

}