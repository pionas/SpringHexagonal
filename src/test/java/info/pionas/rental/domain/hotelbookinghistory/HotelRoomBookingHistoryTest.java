package info.pionas.rental.domain.hotelbookinghistory;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

class HotelRoomBookingHistoryTest {


    @Test
    public void shouldCreateHotelRoomBookingHistoryWithAllInformation() {
        String hotelId = "123";
        HotelRoomBookingHistory hotelRoomBookingHistory = new HotelRoomBookingHistory(hotelId);
        LocalDateTime bookingDateTime = LocalDateTime.now();
        String tenantId = "789";
        List<LocalDate> days = ImmutableList.of(
                LocalDate.of(2020, 5, 5),
                LocalDate.of(2020, 5, 6),
                LocalDate.of(2020, 5, 7)
        );
        hotelRoomBookingHistory.add(bookingDateTime, tenantId, days);

        HotelRoomBookingHistoryAssertion
                .assertThat(hotelRoomBookingHistory)

        ;

    }
}