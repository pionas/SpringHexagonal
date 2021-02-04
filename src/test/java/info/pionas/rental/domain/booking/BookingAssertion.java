package info.pionas.rental.domain.booking;

import info.pionas.rental.domain.money.Money;
import info.pionas.rental.domain.period.Period;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static java.util.Arrays.asList;


@RequiredArgsConstructor
public class BookingAssertion {
    private final Booking actual;

    public static BookingAssertion assertThat(Booking actual) {
        return new BookingAssertion(actual);
    }

    public BookingAssertion isOpen() {
        return hasBookingStatusEqualTo(BookingStatus.OPEN);
    }

    public BookingAssertion isAccepted() {
        return hasBookingStatusEqualTo(BookingStatus.ACCEPTED);
    }

    public BookingAssertion isRejected() {
        return hasBookingStatusEqualTo(BookingStatus.REJECTED);
    }

    public BookingAssertion hasIdEqualTo(UUID expected) {
        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("id", expected);
        return this;
    }

    private BookingAssertion hasBookingStatusEqualTo(BookingStatus expected) {
        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("bookingStatus", expected);
        return this;
    }

    public BookingAssertion isEqualToBookingApartment(String rentalPlaceId, String tenantId, String ownerId, Money price, Period period) {
        Assertions.assertThat(actual).isEqualTo(Booking.apartment(rentalPlaceId, tenantId, ownerId, price, period));
        return this;
    }

    @Deprecated
    public BookingAssertion isEqualToBookingHotelRoom(String rentalPlaceId, String tenantId, List<LocalDate> days) {
        Assertions.assertThat(actual).isEqualTo(Booking.hotelRoom(rentalPlaceId, tenantId, days));
        return this;
    }

    BookingAssertion containsAllDays(LocalDate... expected) {
        return containsAllDays(asList(expected));
    }

    public BookingAssertion containsAllDays(List<LocalDate> expected) {
        Assertions.assertThat(actual).extracting("days").satisfies(days -> {
            List<LocalDate> actualDays = (List<LocalDate>) days;
            Assertions.assertThat(actualDays).containsExactlyElementsOf(expected);
        });
        return this;
    }
}