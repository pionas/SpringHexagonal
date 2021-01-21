package info.pionas.rental.domain.apartment;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;

import java.time.LocalDate;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * @author Adi
 */
@RequiredArgsConstructor
public
class BookingAssertion {

    private final Booking actual;

    public static BookingAssertion assertThat(Booking booking) {
        return new BookingAssertion(booking);
    }

    public BookingAssertion isApartment() {
        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("rentalType", RentalType.APARTMENT);
        return this;
    }

    public BookingAssertion isHotelRoom() {
        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("rentalType", RentalType.HOTEL_ROOM);
        return this;
    }

    BookingAssertion isOpen() {
        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("bookingStatus", BookingStatus.OPEN);
        return this;
    }

    public BookingAssertion isAccept() {
        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("bookingStatus", BookingStatus.ACCEPTED);
        return this;
    }

    public BookingAssertion isReject() {
        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("bookingStatus", BookingStatus.REJECTED);
        return this;
    }

    public BookingAssertion hasRentalPlaceIdEqualTo(String expected) {
        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("rentalPlaceId", expected);
        return this;
    }

    public BookingAssertion hasTenantIdEqualTo(String expected) {
        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("tenantId", expected);
        return this;
    }

    public BookingAssertion hasBookingPeriodThatHas(LocalDate start, LocalDate end) {
        Period period = new Period(start, end);
        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("days", period.asDays());
        return this;
    }

    BookingAssertion containsAllDays(LocalDate... expected) {
        return containsAllDays(asList(expected));
    }

    public BookingAssertion containsAllDays(List<LocalDate> expected) {
        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("days", expected);
        return this;
    }
}
