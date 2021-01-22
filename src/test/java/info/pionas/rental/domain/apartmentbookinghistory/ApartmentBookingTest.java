package info.pionas.rental.domain.apartmentbookinghistory;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static info.pionas.rental.domain.apartmentbookinghistory.ApartmentBookingAssertion.assertThat;

class ApartmentBookingTest {

    @Test
    void shouldCreateStartApartmentBookingWithAllRequiredInformation() {
        LocalDateTime bookingDateTime = LocalDateTime.of(2020, 1, 2, 3, 4);
        String ownerId = "123";
        String tenantId = "234";
        LocalDate start = LocalDate.of(2020, 2, 1);
        LocalDate end = LocalDate.of(2020, 2, 8);

        ApartmentBooking actual = ApartmentBooking.start(bookingDateTime, ownerId, tenantId, new BookingPeriod(start, end));

        assertThat(actual)
                .isStart()
                .hasBookingDateTimeEqualTo(bookingDateTime)
                .hasOwnerIdEqualTo(ownerId)
                .hasTenantIdEqualTo(tenantId)
                .hasBookingPeriodThatHas(start, end);
    }
}