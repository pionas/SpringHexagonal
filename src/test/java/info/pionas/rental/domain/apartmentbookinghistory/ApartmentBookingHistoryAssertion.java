package info.pionas.rental.domain.apartmentbookinghistory;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.AbstractObjectAssert;
import org.assertj.core.api.Assertions;

import java.util.List;
import java.util.function.Consumer;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ApartmentBookingHistoryAssertion {
    private final ApartmentBookingHistory actual;

    public static ApartmentBookingHistoryAssertion assertThat(ApartmentBookingHistory actual) {
        return new ApartmentBookingHistoryAssertion(actual);
    }

    ApartmentBookingHistoryAssertion hasApartmentIdEqualsTo(String expected) {
        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("apartmentId", expected);
        return this;
    }

    public ApartmentBookingHistoryAssertion hasOneApartmentBooking() {
        return hasApartmentBookingsAmount(1);
    }

    public ApartmentBookingHistoryAssertion hasApartmentBookingsAmount(int expected) {
        hasApartmentBookings().satisfies(actualBookings -> {
            Assertions.assertThat(asApartmentBookings(actualBookings)).hasSize(expected);
        });

        return this;
    }

    public ApartmentBookingHistoryAssertion hasApartmentBookingThatSatisfies(Consumer<ApartmentBooking> requirements) {
        hasApartmentBookings().satisfies(actualBookings -> {
            Assertions.assertThat(asApartmentBookings(actualBookings)).anySatisfy(requirements);
        });

        return this;
    }

    private AbstractObjectAssert<?, ?> hasApartmentBookings() {
        return Assertions.assertThat(actual).extracting("bookings");
    }

    private List<ApartmentBooking> asApartmentBookings(Object actualBookings) {
        return (List<ApartmentBooking>) actualBookings;
    }
}