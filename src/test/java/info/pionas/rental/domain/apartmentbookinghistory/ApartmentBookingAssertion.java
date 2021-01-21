package info.pionas.rental.domain.apartmentbookinghistory;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Adi
 */
@RequiredArgsConstructor
public class ApartmentBookingAssertion {

    private final ApartmentBooking actual;

    public static ApartmentBookingAssertion assertThat(ApartmentBooking apartmentBooking) {
        return new ApartmentBookingAssertion(apartmentBooking);
    }

    ApartmentBookingAssertion isStart() {
        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("bookingStep", BookingStep.START);
        return this;
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

    public ApartmentBookingAssertion hasBookingPeriodThatHas(LocalDate start, LocalDate end) {
        Assertions.assertThat(actual)
                .hasFieldOrPropertyWithValue("bookingPeriod.periodStart", start)
                .hasFieldOrPropertyWithValue("bookingPeriod.periodEnd", end);
        return this;
    }

}
