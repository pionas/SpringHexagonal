package info.pionas.rental.domain.hotelroom;

import info.pionas.rental.domain.clock.Clock;
import info.pionas.rental.domain.event.EventIdFactory;
import info.pionas.rental.domain.eventchannel.EventChannel;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class HotelRoomEventsPublisher {
    private final EventIdFactory eventIdFactory;
    private final Clock clock;
    private final EventChannel eventChannel;

    void publishHotelRoomBooked(String hotelRoomId, String hotelId, String tenantId, List<LocalDate> days) {
        HotelRoomBooked hotelRoomBooked = new HotelRoomBooked(eventIdFactory.create(), clock.now(), hotelRoomId, hotelId, tenantId, days);
        eventChannel.publish(hotelRoomBooked);
    }
}
