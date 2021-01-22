package info.pionas.rental.domain.hotelbookinghistory;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.Arrays.asList;

class HotelBookingHistoryTest {
    private static final String HOTEL_ID = "hotelId1";
    private static final String HOTEL_ROOM_ID = "hotelRoomId1";
    private static final LocalDateTime BOOKING_DATE_TIME = LocalDateTime.of(2020, 1, 2, 3, 4);
    private static final String TENANT_ID = "tenantId1";
    private static final List<LocalDate> DAYS = asList(LocalDate.of(2020, 5, 6), LocalDate.of(2020, 7, 8));

    @Test
    void shouldAddFirstHotelRoomBookingHistoryForHotelRoom() {
        HotelBookingHistory hotelBookingHistory = new HotelBookingHistory(HOTEL_ID);

        hotelBookingHistory.add(HOTEL_ROOM_ID, BOOKING_DATE_TIME, TENANT_ID, DAYS);

        HotelBookingHistoryAssertion.assertThat(hotelBookingHistory)
                .hasInformationAboutHistoryOfHotelRooms(1)
                .hasInformationAboutHistoryOfHotelRoom(HOTEL_ROOM_ID, 1)
                .hasHotelRoomBookingHistoryFor(HOTEL_ROOM_ID, BOOKING_DATE_TIME, TENANT_ID, DAYS);
    }

    @Test
    void shouldAddNextHotelRoomBookingHistoryForHotelRoom() {
        LocalDateTime bookingDateTime1 = LocalDateTime.of(2020, 10, 11, 20, 21);
        LocalDateTime bookingDateTime2 = LocalDateTime.of(2020, 1, 2, 3, 4);
        String tenantId1 = "tenantId1";
        String tenantId2 = "tenantId2";
        List<LocalDate> days1 = asList(LocalDate.of(2020, 1, 2), LocalDate.of(2020, 3, 8));
        List<LocalDate> days2 = asList(LocalDate.of(2020, 5, 6));
        HotelBookingHistory hotelBookingHistory = new HotelBookingHistory(HOTEL_ID);

        hotelBookingHistory.add(HOTEL_ROOM_ID, bookingDateTime1, tenantId1, days1);
        hotelBookingHistory.add(HOTEL_ROOM_ID, bookingDateTime2, tenantId2, days2);

        HotelBookingHistoryAssertion.assertThat(hotelBookingHistory)
                .hasInformationAboutHistoryOfHotelRooms(1)
                .hasInformationAboutHistoryOfHotelRoom(HOTEL_ROOM_ID, 2)
                .hasHotelRoomBookingHistoryFor(HOTEL_ROOM_ID, bookingDateTime1, tenantId1, days1)
                .hasHotelRoomBookingHistoryFor(HOTEL_ROOM_ID, bookingDateTime2, tenantId2, days2);
    }

    @Test
    void shouldAddHotelRoomBookingHistoriesForHotelRooms() {
        LocalDateTime bookingDateTime1 = LocalDateTime.of(2020, 10, 11, 20, 21);
        LocalDateTime bookingDateTime2 = LocalDateTime.of(2020, 1, 2, 3, 4);
        String tenantId1 = "tenantId1";
        String tenantId2 = "tenantId2";
        String hotelRoomId2 = "hotelRoomId2";
        List<LocalDate> days1 = asList(LocalDate.of(2020, 1, 2), LocalDate.of(2020, 3, 8));
        List<LocalDate> days2 = asList(LocalDate.of(2020, 5, 6));
        List<LocalDate> days3 = asList(LocalDate.of(2020, 5, 6), LocalDate.of(2020, 7, 8), LocalDate.of(2020, 9, 10));
        HotelBookingHistory hotelBookingHistory = new HotelBookingHistory(HOTEL_ID);

        hotelBookingHistory.add(HOTEL_ROOM_ID, bookingDateTime1, tenantId1, days1);
        hotelBookingHistory.add(HOTEL_ROOM_ID, bookingDateTime2, tenantId2, days2);
        hotelBookingHistory.add(hotelRoomId2, bookingDateTime2, tenantId1, days3);

        HotelBookingHistoryAssertion.assertThat(hotelBookingHistory)
                .hasInformationAboutHistoryOfHotelRooms(2)
                .hasInformationAboutHistoryOfHotelRoom(HOTEL_ROOM_ID, 2)
                .hasInformationAboutHistoryOfHotelRoom(hotelRoomId2, 1)
                .hasHotelRoomBookingHistoryFor(HOTEL_ROOM_ID, bookingDateTime1, tenantId1, days1)
                .hasHotelRoomBookingHistoryFor(HOTEL_ROOM_ID, bookingDateTime2, tenantId2, days2)
                .hasHotelRoomBookingHistoryFor(hotelRoomId2, bookingDateTime2, tenantId1, days3);
    }
}