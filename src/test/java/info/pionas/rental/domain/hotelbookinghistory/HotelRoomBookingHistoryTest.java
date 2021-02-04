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

class HotelRoomBookingHistoryTest {
    private static final int HOTEL_ROOM_NUMBER_1 = 7;
    private static final LocalDateTime BOOKING_DATE_TIME_1 = LocalDateTime.of(2020, 1, 2, 3, 4);
    private static final String TENANT_ID_1 = "tenantId1";
    private static final List<LocalDate> DAYS_1 = asList(LocalDate.of(2020, 5, 6), LocalDate.of(2020, 7, 8));
    private static final int HOTEL_ROOM_NUMBER_2 = 13;
    private static final LocalDateTime BOOKING_DATE_TIME_2 = LocalDateTime.of(2020, 1, 3, 5, 7);
    private static final String TENANT_ID_2 = "1212";
    private static final List<LocalDate> DAYS_2 = asList(LocalDate.of(2020, 9, 10), LocalDate.of(2020, 11, 12));
    private static final LocalDateTime BOOKING_DATE_TIME_3 = LocalDateTime.of(2020, 1, 2, 3, 4);

    @Test
    void shouldRecognizeTheSameInstanceAsTheSameAggregate() {
        HotelRoomBookingHistory actual = givenHotelRoomBookingHistory();

        Assertions.assertThat(actual.equals(actual)).isTrue();
        Assertions.assertThat(actual.hashCode()).isEqualTo(actual.hashCode());
    }

    @Test
    void shouldRecognizeTwoInstancesOfHotelRoomBookingHistoryRepresentsTheSameAggregate() {
        HotelRoomBookingHistory toCompare = givenHotelRoomBookingHistory();

        HotelRoomBookingHistory actual = givenHotelRoomBookingHistory();

        Assertions.assertThat(actual.equals(toCompare)).isTrue();
        Assertions.assertThat(actual.hashCode()).isEqualTo(toCompare.hashCode());
    }

    @Test
    void shouldRecognizeTwoInstancesOfHotelRoomBookingHistoryRepresentsTheSameAggregateEvenWithDifferentHotelRoomBooking() {
        HotelRoomBookingHistory toCompare = givenHotelRoomBookingHistory();
        toCompare.add(BOOKING_DATE_TIME_3, TENANT_ID_2, DAYS_2);

        HotelRoomBookingHistory actual = givenHotelRoomBookingHistory();

        Assertions.assertThat(actual.equals(toCompare)).isTrue();
        Assertions.assertThat(actual.hashCode()).isEqualTo(toCompare.hashCode());
    }

    @Test
    void shouldRecognizeNullIsNotTheSameAsHotelRoomBookingHistory() {
        HotelRoomBookingHistory actual = givenHotelRoomBookingHistory();

        Assertions.assertThat(actual.equals(null)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("notTeSameHotelRoomBookingHistories")
    void shouldRecognizeHotelRoomBookingHistoriesDoesNotRepresentTheSameAggregate(Object toCompare) {
        HotelRoomBookingHistory actual = givenHotelRoomBookingHistory();

        Assertions.assertThat(actual.equals(toCompare)).isFalse();
        Assertions.assertThat(actual.hashCode()).isNotEqualTo(toCompare.hashCode());
    }

    private HotelRoomBookingHistory givenHotelRoomBookingHistory() {
        HotelRoomBookingHistory hotelRoomBookingHistory = new HotelRoomBookingHistory(HOTEL_ROOM_NUMBER_1);
        hotelRoomBookingHistory.add(BOOKING_DATE_TIME_1, TENANT_ID_1, DAYS_1);
        hotelRoomBookingHistory.add(BOOKING_DATE_TIME_2, TENANT_ID_2, DAYS_2);

        return hotelRoomBookingHistory;
    }

    private static Stream<Object> notTeSameHotelRoomBookingHistories() {
        return Stream.of(
                new HotelRoomBookingHistory(HOTEL_ROOM_NUMBER_2),
                new Object()
        );
    }
}