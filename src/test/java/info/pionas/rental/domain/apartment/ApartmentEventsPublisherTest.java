package info.pionas.rental.domain.apartment;

import info.pionas.rental.domain.event.EventIdFactory;
import info.pionas.rental.domain.eventchannel.EventChannel;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

class ApartmentEventsPublisherTest {
    private EventChannel eventChannel = mock(EventChannel.class);
    private final ApartmentEventsPublisher publisher = new ApartmentEventsPublisher(new EventIdFactory(), eventChannel);

    @Test
    void shouldCreateApartmentBooked() {
        ArgumentCaptor<ApartmentBooked> captor = ArgumentCaptor.forClass(ApartmentBooked.class);
        String apartmentId = "123";
        String ownerId = "43";
        String tenantId = "43212";
        LocalDateTime beforeNow = LocalDateTime.now().minusNanos(1);
        LocalDate start = LocalDate.of(2020, 10, 11);
        LocalDate end = LocalDate.of(2020, 10, 18);
        Period period = new Period(start, end);

        publisher.publishApartmentBooked(apartmentId, ownerId, tenantId, period);
        then(eventChannel).should().publish(captor.capture());
        ApartmentBooked actual = captor.getValue();
        assertThat(actual.getEventId()).matches(Pattern.compile("[0-9a-z\\-]{36}"));
        assertThat(actual.getEventCreationDateTime())
                .isAfter(beforeNow)
                .isBefore(LocalDateTime.now().plusNanos(1));
        assertThat(actual.getOwnerId()).isEqualTo(ownerId);
        assertThat(actual.getTenantId()).isEqualTo(tenantId);
        assertThat(actual.getPeriodStart()).isEqualTo(start);
        assertThat(actual.getPeriodEnd()).isEqualTo(end);
    }
}