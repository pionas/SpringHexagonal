package info.pionas.rental.domain.hotelbookinghistory;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.Arrays.asList;

class HotelRoomBookingTest {

    @Test
    void shouldCreateStartHotelRoomBookingWithAllRequiredInformation() {
        LocalDateTime bookingDateTime = LocalDateTime.of(2020, 1, 2, 3, 4);
        String tenantId = "123";
        List<LocalDate> days = asList(LocalDate.of(2020, 5, 6), LocalDate.of(2020, 7, 8));

        HotelRoomBooking actual = HotelRoomBooking.start(bookingDateTime, tenantId, days);

        HotelRoomBookingAssertion.assertThat(actual)
                .hasBookingDateTimeEqualTo(bookingDateTime)
                .hasTenantIdEqualTo(tenantId)
                .containsAllDays(days);
    }
}