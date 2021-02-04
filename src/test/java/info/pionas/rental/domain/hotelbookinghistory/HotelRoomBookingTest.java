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

class HotelRoomBookingTest {

    public static final LocalDateTime BOOKING_DATE_TIME = LocalDateTime.of(2020, 1, 2, 3, 4);
    public static final String TENANT_ID = "123";
    public static final List<LocalDate> DAYS = asList(LocalDate.of(2020, 5, 6), LocalDate.of(2020, 7, 8));

    @Test
    void shouldCreateStartHotelRoomBookingWithAllRequiredInformation() {


        HotelRoomBooking actual = givenHotelRoomBooking();

        HotelRoomBookingAssertion.assertThat(actual)
                .hasBookingDateTimeEqualTo(BOOKING_DATE_TIME)
                .hasTenantIdEqualTo(TENANT_ID)
                .containsAllDays(DAYS);
    }

    @Test
    void shouldRecognizeTheSameInstanceAsTheSameAggregate() {
        HotelRoomBooking actual = givenHotelRoomBooking();

        Assertions.assertThat(actual.equals(actual)).isTrue();
        Assertions.assertThat(actual.hashCode()).isEqualTo(actual.hashCode());
    }

    @Test
    void shouldRecognizeNullIsNotTheSameAsMoney() {
        HotelRoomBooking actual = givenHotelRoomBooking();

        Assertions.assertThat(actual.equals(null)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("notTeSameHotelRoomBooking")
    void shouldRecognizeMoneysDoesNotRepresentTheSameAggregate(Object toCompare) {
        HotelRoomBooking actual = givenHotelRoomBooking();

        Assertions.assertThat(actual.equals(toCompare)).isFalse();
        Assertions.assertThat(actual.hashCode()).isNotEqualTo(toCompare.hashCode());
    }

    private HotelRoomBooking givenHotelRoomBooking() {
        return HotelRoomBooking.start(BOOKING_DATE_TIME, TENANT_ID, DAYS);
    }


    private static Stream<Object> notTeSameHotelRoomBooking() {
        return Stream.of(
                HotelRoomBooking.start(BOOKING_DATE_TIME, "423143", DAYS),
                new Object()
        );
    }
}