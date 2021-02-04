package info.pionas.rental.domain.booking;

import info.pionas.rental.domain.money.Money;
import info.pionas.rental.domain.period.Period;
import info.pionas.rental.domain.rentalplaceidentifier.RentalType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static info.pionas.rental.domain.booking.BookingAssertion.assertThat;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

class BookingTest {
    private static final String RENTAL_PLACE_ID_1 = "5748";
    private static final String TENANT_ID_1 = "1234";
    private static final LocalDate TODAY = LocalDate.now();
    private static final LocalDate TOMORROW = LocalDate.now().plusDays(1);
    private static final List<LocalDate> DAYS_1 = asList(TODAY, TOMORROW);
    private static final String RENTAL_PLACE_ID_2 = "1357";
    private static final String TENANT_ID_2 = "2468";
    private static final List<LocalDate> DAYS_2 = asList(LocalDate.now().minusDays(10), LocalDate.now().plusDays(10));
    private static final String OWNER_ID = "123";
    private static final Money MONEY = Money.of(BigDecimal.valueOf(20));

    private final BookingEventsPublisher bookingEventsPublisher = mock(BookingEventsPublisher.class);

    @Test
    void shouldCreateBookingForApartment() {
        Period period = new Period(LocalDate.of(2020, 3, 4), LocalDate.of(2020, 3, 6));

        Booking actual = Booking.apartment(RENTAL_PLACE_ID_1, TENANT_ID_1, OWNER_ID, MONEY, period);

        assertThat(actual)
                .isOpen()
                .isEqualToBookingApartment(RENTAL_PLACE_ID_1, TENANT_ID_1, OWNER_ID, MONEY, period)
                .containsAllDays(LocalDate.of(2020, 3, 4), LocalDate.of(2020, 3, 5), LocalDate.of(2020, 3, 6));
    }

    @Test
    void shouldCreateBookingForHotelRoom() {
        List<LocalDate> days = asList(LocalDate.of(2020, 6, 1), LocalDate.of(2020, 6, 2), LocalDate.of(2020, 6, 4));

        Booking actual = Booking.hotelRoom(RENTAL_PLACE_ID_1, TENANT_ID_1, days);

        assertThat(actual)
                .isOpen()
                .isEqualToBookingHotelRoom(RENTAL_PLACE_ID_1, TENANT_ID_1, days);
    }

    @Test
    void shouldChangeStatusOfBookingOnceAccepted() {
        Booking booking = givenBooking();

        booking.accept(bookingEventsPublisher);

        assertThat(booking).isAccepted();
    }

    @Test
    void shouldPublishBookingAcceptedOnceAccepted() {
        Booking booking = givenBooking();

        booking.accept(bookingEventsPublisher);

        then(bookingEventsPublisher).should().bookingAccepted(RentalType.HOTEL_ROOM, RENTAL_PLACE_ID_1, TENANT_ID_1, DAYS_1);
    }

    @Test
    void shouldNotAllowToRejectAlreadyAcceptedBooking() {
        Booking booking = givenBooking();
        booking.accept(bookingEventsPublisher);

        NotAllowedBookingStatusTransitionException actual = assertThrows(NotAllowedBookingStatusTransitionException.class, () -> booking.reject(bookingEventsPublisher));

        Assertions.assertThat(actual).hasMessage("Not allowed transition from ACCEPTED to REJECTED booking");
        assertThat(booking).isAccepted();
    }

    @Test
    void shouldChangeStatusOfBookingOnceRejected() {
        Booking booking = givenBooking();

        booking.reject(bookingEventsPublisher);

        assertThat(booking).isRejected();
    }

    @Test
    void shouldPublishBookingRejectedOnceAccepted() {
        Booking booking = givenBooking();

        booking.reject(bookingEventsPublisher);

        then(bookingEventsPublisher).should().bookingRejected(RentalType.HOTEL_ROOM, RENTAL_PLACE_ID_1, TENANT_ID_1, DAYS_1);
    }

    @Test
    void shouldNotAllowToAcceptAlreadyRejectedBooking() {
        Booking booking = givenBooking();
        booking.reject(bookingEventsPublisher);

        NotAllowedBookingStatusTransitionException actual = assertThrows(NotAllowedBookingStatusTransitionException.class, () -> booking.accept(bookingEventsPublisher));

        Assertions.assertThat(actual).hasMessage("Not allowed transition from REJECTED to ACCEPTED booking");
        assertThat(booking).isRejected();
    }

    @Test
    void shouldRecognizeTheSameInstanceAsTheSameAggregate() {
        Booking actual = givenBooking();

        Assertions.assertThat(actual.equals(actual)).isTrue();
        Assertions.assertThat(actual.hashCode()).isEqualTo(actual.hashCode());
    }

    @Test
    void shouldRecognizeTwoInstancesOfBookingRepresentsTheSameAggregate() {
        Booking toCompare = givenBooking();

        Booking actual = givenBooking();

        Assertions.assertThat(actual.equals(toCompare)).isTrue();
        Assertions.assertThat(actual.hashCode()).isEqualTo(toCompare.hashCode());
    }

    @Test
    void shouldRecognizeRejectedBookingAsTheSameAggregate() {
        Booking toCompare = givenBooking();
        toCompare.reject(bookingEventsPublisher);

        Booking actual = givenBooking();

        Assertions.assertThat(actual.equals(toCompare)).isTrue();
        Assertions.assertThat(actual.hashCode()).isEqualTo(toCompare.hashCode());
    }

    @Test
    void shouldRecognizeAcceptedBookingAsTheSameAggregate() {
        Booking toCompare = givenBooking();
        toCompare.accept(bookingEventsPublisher);

        Booking actual = givenBooking();

        Assertions.assertThat(actual.equals(toCompare)).isTrue();
        Assertions.assertThat(actual.hashCode()).isEqualTo(toCompare.hashCode());
    }

    @Test
    void shouldRecognizeNullIsNotTheSameAsBooking() {
        Booking actual = givenBooking();

        Assertions.assertThat(actual.equals(null)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("notTeSameBookings")
    void shouldRecognizeBookingsDoesNotRepresentTheSameAggregate(Object toCompare) {
        Booking actual = givenBooking();

        Assertions.assertThat(actual.equals(toCompare)).isFalse();
        Assertions.assertThat(actual.hashCode()).isNotEqualTo(toCompare.hashCode());
    }

    private static Stream<Object> notTeSameBookings() {
        return Stream.of(
                Booking.hotelRoom(RENTAL_PLACE_ID_2, TENANT_ID_1, DAYS_1),
                Booking.hotelRoom(RENTAL_PLACE_ID_1, TENANT_ID_2, DAYS_1),
                Booking.hotelRoom(RENTAL_PLACE_ID_1, TENANT_ID_1, DAYS_2),
                Booking.apartment(RENTAL_PLACE_ID_1, TENANT_ID_1, OWNER_ID, MONEY, new Period(TODAY, TOMORROW)),
                new Object()
        );
    }

    private Booking givenBooking() {
        return Booking.hotelRoom(RENTAL_PLACE_ID_1, TENANT_ID_1, DAYS_1);
    }
}