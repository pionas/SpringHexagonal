package info.pionas.rental.domain.booking;

import com.google.common.collect.ImmutableList;
import info.pionas.rental.domain.event.FakeEventIdFactory;
import info.pionas.rental.infrastructure.clock.FakeClock;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static info.pionas.rental.domain.booking.BookingAccepted.Builder.bookingAccepted;
import static info.pionas.rental.domain.rentalplaceidentifier.RentalType.APARTMENT;
import static org.assertj.core.api.Assertions.assertThat;

class BookingAcceptedTest {

    public static final String RENTAL_PLACE_ID = "1234";
    public static final String TENANT_ID = "7890";
    public static final List<LocalDate> DAYS = ImmutableList.of(
            LocalDate.of(2020, 10, 10), LocalDate.of(2020, 10, 11), LocalDate.of(2020, 10, 12));

    @Test
    void shouldCreateBookingAcceptedWithAllRequiredInformation() {
        BookingAccepted actual = getBookingAccepted();

        assertThat(actual.getEventId()).isEqualTo(FakeEventIdFactory.UUID);
        assertThat(actual.getEventCreationDateTime()).isEqualTo(FakeClock.NOW);
        assertThat(actual.getRentalType()).isEqualTo("APARTMENT");
        assertThat(actual.getRentalPlaceId()).isEqualTo(RENTAL_PLACE_ID);
        assertThat(actual.getTenantId()).isEqualTo(TENANT_ID);
        assertThat(actual.getDays()).containsExactlyElementsOf(DAYS);
    }

    private BookingAccepted getBookingAccepted() {
        return bookingAccepted()
                .withEventId(FakeEventIdFactory.UUID)
                .withEventCreationDateTime(new FakeClock().now())
                .withRentalType(APARTMENT.name())
                .withRentalPlaceId(RENTAL_PLACE_ID)
                .withTenantId(TENANT_ID)
                .withDays(DAYS)
                .build();
    }
}
