package info.pionas.rental.domain.hotel;

import info.pionas.rental.domain.event.FakeEventIdFactory;
import info.pionas.rental.domain.eventchannel.EventChannel;
import info.pionas.rental.infrastructure.clock.FakeClock;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

class HotelRoomEventsPublisherTest {
    private final EventChannel eventChannel = mock(EventChannel.class);
    private final HotelEventsPublisher publisher = new HotelEventsPublisher(new FakeEventIdFactory(), new FakeClock(), eventChannel);

    @Test
    void shouldPublishHotelRoomBookedEvent() {
        ArgumentCaptor<HotelRoomBooked> captor = ArgumentCaptor.forClass(HotelRoomBooked.class);
        int hotelRoomNumber = 1234;
        String hotelId = "5678";
        String tenantId = "3456";
        List<LocalDate> days = asList(LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 2), LocalDate.of(2020, 1, 3));

        publisher.publishHotelRoomBooked(hotelId, hotelRoomNumber, tenantId, days);

        then(eventChannel).should().publish(captor.capture());
        HotelRoomBooked actual = captor.getValue();
        assertThat(actual.getEventId()).isEqualTo(FakeEventIdFactory.UUID);
        assertThat(actual.getEventCreationDateTime()).isEqualTo(FakeClock.NOW);
        assertThat(actual.getHotelRoomNumber()).isEqualTo(hotelRoomNumber);
        assertThat(actual.getHotelId()).isEqualTo(hotelId);
        assertThat(actual.getTenantId()).isEqualTo(tenantId);
        assertThat(actual.getDays()).containsExactlyElementsOf(days);
    }
}