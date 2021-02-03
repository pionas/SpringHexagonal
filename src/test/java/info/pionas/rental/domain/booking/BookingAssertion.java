package info.pionas.rental.domain.booking;

import info.pionas.rental.domain.money.Money;
import info.pionas.rental.domain.period.Period;
import info.pionas.rental.domain.rentalplaceidentifier.RentalType;
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

    public BookingAssertion hasIdEqualTo(String expected) {
        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("id", UUID.fromString(expected));
        return this;
    }

    private BookingAssertion hasBookingStatusEqualTo(BookingStatus expected) {
        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("bookingStatus", expected);
        return this;
    }

    public BookingAssertion isApartment() {
        return hasRentalTypeEqualTo(RentalType.APARTMENT);
    }

    public BookingAssertion isHotelRoom() {
        return hasRentalTypeEqualTo(RentalType.HOTEL_ROOM);
    }

    private BookingAssertion hasRentalTypeEqualTo(RentalType rentalType) {
        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("rentalType", rentalType);
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

    public BookingAssertion hasOwnerIdEqualTo(String expected) {
        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("ownerId", expected);
        return this;
    }

    public BookingAssertion hasPriceEqualTo(Money expected) {
        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("price", expected);
        return this;
    }

    public BookingAssertion containsAllDays(LocalDate... expected) {
        return containsAllDays(asList(expected));
    }

    public BookingAssertion containsAllDays(List<LocalDate> expected) {
        Assertions.assertThat(actual).extracting("days").satisfies(days -> {
            List<LocalDate> actualDays = (List<LocalDate>) days;
            Assertions.assertThat(actualDays).containsExactlyElementsOf(expected);
        });
        return this;
    }

    public BookingAssertion isEqualToBookingApartment(String rentalPlaceId, String tenantId, String ownerId, Money price, Period period) {
        Assertions.assertThat(actual).isEqualTo(Booking.apartment(rentalPlaceId, tenantId, ownerId, price, period));
        return this;
    }
}