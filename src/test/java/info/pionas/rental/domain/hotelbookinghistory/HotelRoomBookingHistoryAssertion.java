package info.pionas.rental.domain.hotelbookinghistory;


import org.assertj.core.api.AbstractObjectAssert;
import org.assertj.core.api.Assertions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Consumer;

class HotelRoomBookingHistoryAssertion {
    private final HotelRoomBookingHistory actual;

    private HotelRoomBookingHistoryAssertion(HotelRoomBookingHistory actual) {
        this.actual = actual;
    }

    static HotelRoomBookingHistoryAssertion assertThat(HotelRoomBookingHistory actual) {
        return new HotelRoomBookingHistoryAssertion(actual);
    }

    HotelRoomBookingHistoryAssertion hasHotelRoomIdEqualTo(String expected) {
        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("hotelRoomId", expected);
        return this;
    }

    HotelRoomBookingHistoryAssertion hasHotelRoomBookingFor(LocalDateTime bookingDateTime, String tenantId, List<LocalDate> days) {
        return hasHotelRoomBookingFor(hotelRoomBooking -> {
            Assertions.assertThat(hotelRoomBooking)
                    .hasFieldOrPropertyWithValue("bookingDateTime", bookingDateTime)
                    .hasFieldOrPropertyWithValue("tenantId", tenantId)
                    .hasFieldOrPropertyWithValue("days", days);
        });
    }

    HotelRoomBookingHistoryAssertion hasHotelRoomBookingFor(String tenantId, List<LocalDate> days) {
        return hasHotelRoomBookingFor(hotelRoomBooking -> {
            Assertions.assertThat(hotelRoomBooking)
                    .hasFieldOrPropertyWithValue("tenantId", tenantId)
                    .hasFieldOrPropertyWithValue("days", days);
        });
    }

    private HotelRoomBookingHistoryAssertion hasHotelRoomBookingFor(Consumer<HotelRoomBooking> consumer) {
        hasHotelRoomBookings().satisfies(bookings -> {
            Assertions.assertThat(asHotelRoomBookings(bookings)).anySatisfy(consumer);
        });

        return this;
    }

    HotelRoomBookingHistoryAssertion hasInformationAboutBookings(int size) {
        hasHotelRoomBookings().satisfies(bookings -> {
            Assertions.assertThat(asHotelRoomBookings(bookings)).hasSize(size);
        });

        return this;
    }

    private AbstractObjectAssert<?, ?> hasHotelRoomBookings() {
        return Assertions.assertThat(actual).extracting("bookings");
    }

    private List<HotelRoomBooking> asHotelRoomBookings(Object actualBookings) {
        return (List<HotelRoomBooking>) actualBookings;
    }
}