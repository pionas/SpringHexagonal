package info.pionas.rental.domain.hotelbookinghistory;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class HotelBookingHistoryTest {

    @Test
    public void shouldCreateHotelBookingHistoryWithAllInformation() {
        String hotelId = "123";
        HotelBookingHistory hotelBookingHistory = new HotelBookingHistory(hotelId);
        String hotelRoomId = "456";
        LocalDateTime bookingDateTime = LocalDateTime.now();
        String tenantId = "789";
        List<LocalDate> days = ImmutableList.of(
                LocalDate.of(2020, 5, 5),
                LocalDate.of(2020, 5, 6),
                LocalDate.of(2020, 5, 7)
        );
        hotelBookingHistory.add(hotelRoomId, bookingDateTime, tenantId, days);

        HotelBookingHistoryAssertion.assertThat(hotelBookingHistory)
                .hasInformationAboutHistoryOfHotelRooms(1)
                .hasInformationAboutHistoryOfHotelRoom(hotelRoomId, 1)
                .hasHotelRoomBookingHistoryFor(hotelRoomId, bookingDateTime, tenantId, days);

    }
}
