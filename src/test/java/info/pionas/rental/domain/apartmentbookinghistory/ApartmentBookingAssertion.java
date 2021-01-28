package info.pionas.rental.domain.apartmentbookinghistory;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ApartmentBookingAssertion {
    private final ApartmentBooking actual;

    public static ApartmentBookingAssertion assertThat(ApartmentBooking actual) {
        return new ApartmentBookingAssertion(actual);
    }

    ApartmentBookingAssertion hasBookingDateTimeEqualTo(LocalDateTime expected) {
        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("bookingDateTime", expected);
        return this;
    }

    public ApartmentBookingAssertion hasOwnerIdEqualTo(String expected) {
        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("ownerId", expected);
        return this;
    }

    public ApartmentBookingAssertion hasTenantIdEqualTo(String expected) {
        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("tenantId", expected);
        return this;
    }

    public ApartmentBookingAssertion hasPeriodThatHas(LocalDate expectedStart, LocalDate expectedEnd) {
        Assertions.assertThat(actual)
                .hasFieldOrPropertyWithValue("period.periodStart", expectedStart)
                .hasFieldOrPropertyWithValue("period.periodEnd", expectedEnd);
        return this;
    }

    public ApartmentBookingAssertion isStart() {
        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("bookingStep", BookingStep.START);
        return this;
    }
}