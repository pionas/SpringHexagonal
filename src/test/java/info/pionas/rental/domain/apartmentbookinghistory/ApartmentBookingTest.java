package info.pionas.rental.domain.apartmentbookinghistory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Adi
 */
public class ApartmentBookingTest {

    @Test
    public void shouldCreateApartmentBookingWithALlRequiredInformation() {
        LocalDateTime bookingDateTime = LocalDateTime.of(2020, 1, 1, 12, 0);
        String ownerId = "123";
        String tenantId = "456";
        LocalDate start = LocalDate.of(2020, 2, 1);
        LocalDate end = LocalDate.of(2020, 2, 8);

        ApartmentBooking actual = ApartmentBooking.start(bookingDateTime, ownerId, tenantId, new BookingPeriod(start, end));

        ApartmentBookingAssertion
                .assertThat(actual)
                .isStart()
                .hasBookingDateTimeEqualTo(bookingDateTime)
                .hasOwnerIdEqualTo(ownerId)
                .hasTenantIdEqualTo(tenantId)
                .hasBookingPeriodThatHas(start, end);
    }
}
