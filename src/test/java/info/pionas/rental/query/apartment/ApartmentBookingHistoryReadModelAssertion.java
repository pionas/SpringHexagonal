package info.pionas.rental.query.apartment;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
class ApartmentBookingHistoryReadModelAssertion {
    private final ApartmentBookingHistoryReadModel actual;

    static ApartmentBookingHistoryReadModelAssertion assertThat(ApartmentBookingHistoryReadModel actual) {
        return new ApartmentBookingHistoryReadModelAssertion(actual);
    }

    ApartmentBookingHistoryReadModelAssertion hasApartmentIdEqualsTo(String apartmentId) {
        Assertions.assertThat(actual.getApartmentId()).isEqualTo(apartmentId);
        return this;
    }

    ApartmentBookingHistoryReadModelAssertion hasOneApartmentBooking() {
        Assertions.assertThat(actual.getBookings()).hasSize(1);
        return this;
    }

    ApartmentBookingHistoryReadModelAssertion hasApartmentBookingFor(
            LocalDateTime bookingDateTime, String ownerId, String tenantId, LocalDate bookingStart, LocalDate bookingEnd) {
        Assertions.assertThat(actual.getBookings()).anySatisfy(apartmentBookingReadModel -> {
            Assertions.assertThat(apartmentBookingReadModel.getBookingStep()).isEqualTo("START");
            Assertions.assertThat(apartmentBookingReadModel.getBookingDateTime()).isEqualTo(bookingDateTime);
            Assertions.assertThat(apartmentBookingReadModel.getOwnerId()).isEqualTo(ownerId);
            Assertions.assertThat(apartmentBookingReadModel.getTenantId()).isEqualTo(tenantId);
            Assertions.assertThat(apartmentBookingReadModel.getPeriodStart()).isEqualTo(bookingStart);
            Assertions.assertThat(apartmentBookingReadModel.getPeriodEnd()).isEqualTo(bookingEnd);
        });

        return this;
    }
}