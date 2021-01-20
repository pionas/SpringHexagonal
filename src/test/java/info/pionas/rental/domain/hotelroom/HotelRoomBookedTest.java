package info.pionas.rental.domain.hotelroom;

import com.google.common.collect.ImmutableList;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Pattern;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Adi
 */
public class HotelRoomBookedTest {

    @Test
    public void shouldCreateHotelRoomBookedWithAllInformation() {
        String hotelRoomId = "123";
        String hotelId = "456";
        String tenantId = "789";
        List<LocalDate> days = ImmutableList.of(
                LocalDate.of(2020, 5, 5),
                LocalDate.of(2020, 5, 6),
                LocalDate.of(2020, 5, 7)
        );
        LocalDateTime beforeNow = LocalDateTime.now().minusNanos(1);

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
