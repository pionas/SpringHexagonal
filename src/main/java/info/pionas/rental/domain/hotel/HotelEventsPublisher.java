package info.pionas.rental.domain.hotel;

import info.pionas.rental.domain.clock.Clock;
import info.pionas.rental.domain.event.EventIdFactory;
import info.pionas.rental.domain.eventchannel.EventChannel;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class HotelEventsPublisher {
    private final EventIdFactory eventIdFactory;
    private final Clock clock;
    private final EventChannel eventChannel;


    void publishHotelRoomBooked(String hotelId, int hotelRoomNumber, String tenantId, List<LocalDate> days) {
        HotelRoomBooked hotelRoomBooked = new HotelRoomBooked(eventIdFactory.create(), clock.now(), hotelRoomNumber, hotelId, tenantId, days);
        eventChannel.publish(hotelRoomBooked);
    }
}
