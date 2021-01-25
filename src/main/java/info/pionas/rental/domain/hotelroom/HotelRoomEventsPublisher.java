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

    public void publishHotelRoomBooked(String hotelRoomId, String hotelId, String tenantId, List<LocalDate> days) {
        HotelRoomBooked hotelRoomBooked = HotelRoomBooked.create(hotelRoomId, hotelId, tenantId, days);
        eventChannel.publish(hotelRoomBooked);
    }
}
