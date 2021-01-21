package info.pionas.rental.query.apartment;

import org.assertj.core.api.Assertions;

import java.time.LocalDate;
import java.time.LocalDateTime;

class ApartmentBookingHistoryReadModelAssertion {
    private final ApartmentBookingHistoryReadModel actual;

    private ApartmentBookingHistoryReadModelAssertion(ApartmentBookingHistoryReadModel actual) {
        this.actual = actual;
    }

    static ApartmentBookingHistoryReadModelAssertion assertThat(ApartmentBookingHistoryReadModel actual) {
        return new ApartmentBookingHistoryReadModelAssertion(actual);
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