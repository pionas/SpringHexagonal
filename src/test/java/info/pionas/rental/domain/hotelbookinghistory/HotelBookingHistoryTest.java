package info.pionas.rental.domain.hotelbookinghistory;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

class HotelBookingHistoryTest {
    private static final String HOTEL_ID_1 = "hotelId1";
    private static final int HOTEL_ROOM_NUMBER_1 = 7;
    private static final LocalDateTime BOOKING_DATE_TIME_1 = LocalDateTime.of(2020, 1, 2, 3, 4);
    private static final String TENANT_ID_1 = "tenantId1";
    private static final List<LocalDate> DAYS_1 = asList(LocalDate.of(2020, 5, 6), LocalDate.of(2020, 7, 8));
    private static final String HOTEL_ID_2 = "hotelId2";
    private static final int HOTEL_ROOM_NUMBER_2 = 13;
    private static final LocalDateTime BOOKING_DATE_TIME_2 = LocalDateTime.of(2020, 1, 3, 5, 7);
    private static final String TENANT_ID_2 = "1212";
    private static final List<LocalDate> DAYS_2 = asList(LocalDate.of(2020, 9, 10), LocalDate.of(2020, 11, 12));
    private static final LocalDateTime BOOKING_DATE_TIME_3 = LocalDateTime.of(2020, 1, 2, 3, 4);

    @Test
    void shouldAddFirstHotelRoomBookingHistoryForHotelRoom() {
        HotelBookingHistory hotelBookingHistory = new HotelBookingHistory(HOTEL_ID_1);

        hotelBookingHistory.add(HOTEL_ROOM_NUMBER_1, BOOKING_DATE_TIME_1, TENANT_ID_1, DAYS_1);

        HotelBookingHistoryAssertion.assertThat(hotelBookingHistory)
                .hasInformationAboutHistoryOfHotelRooms(1)
                .hasInformationAboutHistoryOfHotelRoom(HOTEL_ROOM_NUMBER_1, 1)
                .hasHotelRoomBookingHistoryFor(HOTEL_ROOM_NUMBER_1, BOOKING_DATE_TIME_1, TENANT_ID_1, DAYS_1);
    }

    @Test
    void shouldAddNextHotelRoomBookingHistoryForHotelRoom() {
        LocalDateTime bookingDateTime1 = LocalDateTime.of(2020, 10, 11, 20, 21);
        LocalDateTime bookingDateTime2 = LocalDateTime.of(2020, 1, 2, 3, 4);
        String tenantId1 = "tenantId1";
        String tenantId2 = "tenantId2";
        List<LocalDate> days1 = asList(LocalDate.of(2020, 1, 2), LocalDate.of(2020, 3, 8));
        List<LocalDate> days2 = asList(LocalDate.of(2020, 5, 6));
        HotelBookingHistory hotelBookingHistory = new HotelBookingHistory(HOTEL_ID_1);

        hotelBookingHistory.add(HOTEL_ROOM_NUMBER_1, bookingDateTime1, tenantId1, days1);
        hotelBookingHistory.add(HOTEL_ROOM_NUMBER_1, bookingDateTime2, tenantId2, days2);

        HotelBookingHistoryAssertion.assertThat(hotelBookingHistory)
                .hasInformationAboutHistoryOfHotelRooms(1)
                .hasInformationAboutHistoryOfHotelRoom(HOTEL_ROOM_NUMBER_1, 2)
                .hasHotelRoomBookingHistoryFor(HOTEL_ROOM_NUMBER_1, bookingDateTime1, tenantId1, days1)
                .hasHotelRoomBookingHistoryFor(HOTEL_ROOM_NUMBER_1, bookingDateTime2, tenantId2, days2);
    }

    @Test
    void shouldAddHotelRoomBookingHistoriesForHotelRooms() {
        List<LocalDate> days3 = asList(LocalDate.of(2020, 5, 6), LocalDate.of(2020, 7, 8), LocalDate.of(2020, 9, 10));
        HotelBookingHistory hotelBookingHistory = new HotelBookingHistory(HOTEL_ID_1);

        hotelBookingHistory.add(HOTEL_ROOM_NUMBER_1, BOOKING_DATE_TIME_1, TENANT_ID_1, DAYS_1);
        hotelBookingHistory.add(HOTEL_ROOM_NUMBER_1, BOOKING_DATE_TIME_2, TENANT_ID_2, DAYS_2);
        hotelBookingHistory.add(HOTEL_ROOM_NUMBER_2, BOOKING_DATE_TIME_2, TENANT_ID_1, days3);

        HotelBookingHistoryAssertion.assertThat(hotelBookingHistory)
                .hasInformationAboutHistoryOfHotelRooms(2)
                .hasInformationAboutHistoryOfHotelRoom(HOTEL_ROOM_NUMBER_1, 2)
                .hasInformationAboutHistoryOfHotelRoom(HOTEL_ROOM_NUMBER_2, 1)
                .hasHotelRoomBookingHistoryFor(HOTEL_ROOM_NUMBER_1, BOOKING_DATE_TIME_1, TENANT_ID_1, DAYS_1)
                .hasHotelRoomBookingHistoryFor(HOTEL_ROOM_NUMBER_1, BOOKING_DATE_TIME_2, TENANT_ID_2, DAYS_2)
                .hasHotelRoomBookingHistoryFor(HOTEL_ROOM_NUMBER_2, BOOKING_DATE_TIME_2, TENANT_ID_1, days3);
    }

    @Test
    void shouldRecognizeTheSameInstanceAsTheSameAggregate() {
        HotelBookingHistory actual = givenHotelBookingHistory();

        Assertions.assertThat(actual.equals(actual)).isTrue();
        Assertions.assertThat(actual.hashCode()).isEqualTo(actual.hashCode());
    }

    @Test
    void shouldRecognizeTwoInstancesOfHotelBookingHistoryRepresentsTheSameAggregate() {
        HotelBookingHistory toCompare = givenHotelBookingHistory();

        HotelBookingHistory actual = givenHotelBookingHistory();

        Assertions.assertThat(actual.equals(toCompare)).isTrue();
        Assertions.assertThat(actual.hashCode()).isEqualTo(toCompare.hashCode());
    }

    @Test
    void shouldRecognizeTwoInstancesOfHotelBookingHistoryRepresentsTheSameAggregateEvenWithDifferentHotelBooking() {
        HotelBookingHistory toCompare = givenHotelBookingHistory();
        toCompare.add(HOTEL_ROOM_NUMBER_1, BOOKING_DATE_TIME_3, TENANT_ID_2, DAYS_2);

        HotelBookingHistory actual = givenHotelBookingHistory();

        Assertions.assertThat(actual.equals(toCompare)).isTrue();
        Assertions.assertThat(actual.hashCode()).isEqualTo(toCompare.hashCode());
    }

    @Test
    void shouldRecognizeNullIsNotTheSameAsHotelBookingHistory() {
        HotelBookingHistory actual = givenHotelBookingHistory();

        Assertions.assertThat(actual.equals(null)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("notTeSameHotelBookingHistories")
    void shouldRecognizeHotelBookingHistoriesDoesNotRepresentTheSameAggregate(Object toCompare) {
        HotelBookingHistory actual = givenHotelBookingHistory();

        Assertions.assertThat(actual.equals(toCompare)).isFalse();
        Assertions.assertThat(actual.hashCode()).isNotEqualTo(toCompare.hashCode());
    }

    private HotelBookingHistory givenHotelBookingHistory() {
        HotelBookingHistory hotelBookingHistory = new HotelBookingHistory(HOTEL_ID_1);
        hotelBookingHistory.add(HOTEL_ROOM_NUMBER_1, BOOKING_DATE_TIME_1, TENANT_ID_1, DAYS_1);
        hotelBookingHistory.add(HOTEL_ROOM_NUMBER_1, BOOKING_DATE_TIME_2, TENANT_ID_2, DAYS_2);

        return hotelBookingHistory;
    }

    private static Stream<Object> notTeSameHotelBookingHistories() {
        return Stream.of(
                new HotelBookingHistory(HOTEL_ID_2),
                new Object()
        );
    }
}