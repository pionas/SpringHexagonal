package info.pionas.rental.domain.hotelroom;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Pattern;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;


class HotelRoomBookedTest {
    @Test
    void shouldCreateEventWithAllInformation() {
        String hotelRoomId = "1234";
        String hotelId = "5678";
        String tenantId = "3456";
        LocalDateTime beforeNow = LocalDateTime.now().minusNanos(1);
        List<LocalDate> days = asList(LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 2), LocalDate.of(2020, 1, 3));

        HotelRoomBooked actual = HotelRoomBooked.create(hotelRoomId, hotelId, tenantId, days);

        assertThat(actual.getEventId()).matches(Pattern.compile("[0-9a-z\\-]{36}"));
        assertThat(actual.getEventCreationDateTime())
                .isAfter(beforeNow)
                .isBefore(LocalDateTime.now().plusNanos(1));
        assertThat(actual.getHotelRoomId()).isEqualTo(hotelRoomId);
        assertThat(actual.getHotelId()).isEqualTo(hotelId);
        assertThat(actual.getTenantId()).isEqualTo(tenantId);
        assertThat(actual.getDays()).containsExactlyElementsOf(days);
    }
}