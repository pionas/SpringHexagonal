package info.pionas.rental.domain.hotelbookinghistory;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

class HotelRoomBookingHistoryTest {

    public static final String HOTEL_ID = "123";
    public static final HotelRoomBookingHistory HOTEL_ROOM_BOOKING_HISTORY = new HotelRoomBookingHistory(HOTEL_ID);
    public static final LocalDateTime BOOKING_DATE_TIME = LocalDateTime.now();
    public static final String TENANT_ID = "789";
    public static final List<LocalDate> DAYS = ImmutableList.of(
            LocalDate.of(2020, 5, 5),
            LocalDate.of(2020, 5, 6),
            LocalDate.of(2020, 5, 7)
    );

    @Test
    public void shouldCreateHotelRoomBookingHistoryWithAllInformation() {
        HOTEL_ROOM_BOOKING_HISTORY.add(BOOKING_DATE_TIME, TENANT_ID, DAYS);

        HotelRoomBookingHistoryAssertion
                .assertThat(HOTEL_ROOM_BOOKING_HISTORY)

        ;

    }
}