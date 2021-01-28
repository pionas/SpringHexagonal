package info.pionas.rental.domain.booking;

import com.google.common.collect.ImmutableList;
import info.pionas.rental.domain.clock.Clock;
import info.pionas.rental.domain.event.EventIdFactory;
import info.pionas.rental.domain.eventchannel.EventChannel;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Pattern;

import static info.pionas.rental.domain.booking.RentalType.APARTMENT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

class BookingEventsPublisherTest {
    private EventChannel eventChannel = mock(EventChannel.class);
    private final BookingEventsPublisher publisher = new BookingEventsPublisher(new EventIdFactory(), new Clock(), eventChannel);

    @Test
    void shouldCreateBookingAccepted() {
        ArgumentCaptor<BookingAccepted> captor = ArgumentCaptor.forClass(BookingAccepted.class);
        String rentalPlaceId = "1234";
        String tenantId = "7890";
        List<LocalDate> days = ImmutableList.of(
                LocalDate.of(2020, 10, 10), LocalDate.of(2020, 10, 11), LocalDate.of(2020, 10, 12));
        LocalDateTime beforeNow = LocalDateTime.now().minusNanos(1);

        publisher.publishApartmentBooked(APARTMENT, rentalPlaceId, tenantId, days);
        then(eventChannel).should().publish(captor.capture());
        BookingAccepted actual = captor.getValue();
        assertThat(actual.getEventId()).matches(Pattern.compile("[0-9a-z\\-]{36}"));
        assertThat(actual.getEventCreationDateTime())
                .isAfter(beforeNow)
                .isBefore(LocalDateTime.now().plusNanos(1));
        assertThat(actual.getRentalType()).isEqualTo("APARTMENT");
        assertThat(actual.getRentalPlaceId()).isEqualTo(rentalPlaceId);
        assertThat(actual.getTenantId()).isEqualTo(tenantId);
        assertThat(actual.getDays()).containsExactlyElementsOf(days);
    }
}