package info.pionas.rental.domain.booking;

import com.google.common.collect.ImmutableList;
import info.pionas.rental.domain.clock.Clock;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import static info.pionas.rental.domain.booking.BookingAccepted.Builder.bookingAccepted;
import static info.pionas.rental.domain.booking.RentalType.APARTMENT;
import static org.assertj.core.api.Assertions.assertThat;

class BookingAcceptedTest {

    public static final String RENTAL_PLACE_ID = "1234";
    public static final String TENANT_ID = "7890";
    public static final List<LocalDate> DAYS = ImmutableList.of(
            LocalDate.of(2020, 10, 10), LocalDate.of(2020, 10, 11), LocalDate.of(2020, 10, 12));

    @Test
    void shouldCreateBookingAcceptedWithAllRequiredInformation() {
        LocalDateTime beforeNow = LocalDateTime.now().minusNanos(1);

        BookingAccepted actual = getBookingAccepted();

        assertThat(actual.getEventId()).matches(Pattern.compile("[0-9a-z\\-]{36}"));
        assertThat(actual.getEventCreationDateTime())
                .isAfter(beforeNow)
                .isBefore(LocalDateTime.now().plusNanos(1));
        assertThat(actual.getRentalType()).isEqualTo("APARTMENT");
        assertThat(actual.getRentalPlaceId()).isEqualTo(RENTAL_PLACE_ID);
        assertThat(actual.getTenantId()).isEqualTo(TENANT_ID);
        assertThat(actual.getDays()).containsExactlyElementsOf(DAYS);
    }

    private BookingAccepted getBookingAccepted() {
        return bookingAccepted()
                .withEventId(UUID.randomUUID().toString())
                .withEventCreationDateTime(new Clock().now())
                .withRentalType(APARTMENT.name())
                .withRentalPlaceId(RENTAL_PLACE_ID)
                .withTenantId(TENANT_ID)
                .withDays(DAYS)
                .build();
    }
}
