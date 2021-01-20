package info.pionas.rental.domain.apartment;

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
public class BookingAcceptedTest {

    @Test
    public void shouldCreateBookingAcceptedWithAllInformation() {
        LocalDateTime beforeNow = LocalDateTime.now().minusNanos(1);
        RentalType rentalType = RentalType.APARTMENT;
        String rentalPlaceId = "5678";
        String tenantId = "3456";
        List<LocalDate> days = ImmutableList.of(
                LocalDate.of(2020, 5, 5),
                LocalDate.of(2020, 5, 6),
                LocalDate.of(2020, 5, 7)
        );

        BookingAccepted actual = BookingAccepted.create(rentalType, rentalPlaceId, tenantId, days);

        assertThat(actual.getEventId()).matches(Pattern.compile("[0-9a-z\\-]{36}"));
        assertThat(actual.getEventCreationDateTime())
                .isAfter(beforeNow)
                .isBefore(LocalDateTime.now().plusNanos(1));
        assertThat(actual.getName()).isEqualTo("APARTMENT");
        assertThat(actual.getRentalPlaceId()).isEqualTo(rentalPlaceId);
        assertThat(actual.getTenantId()).isEqualTo(tenantId);
        assertThat(actual.getDays()).containsExactlyElementsOf(days);
    }

}
