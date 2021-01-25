package info.pionas.rental.domain.hotelroom;

import info.pionas.rental.domain.clock.Clock;
import info.pionas.rental.domain.event.EventIdFactory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@RequiredArgsConstructor
@Getter
public class HotelRoomBooked {

    private final String eventId;
    private final LocalDateTime eventCreationDateTime;
    private final String hotelRoomId;
    private final String hotelId;
    private final String tenantId;
    private final List<LocalDate> days;

    static HotelRoomBooked create(String hotelRoomId, String hotelId, String tenantId, List<LocalDate> days) {
        String eventId = new EventIdFactory().create();
        LocalDateTime eventCreationDateTime = LocalDateTime.now();
        return new HotelRoomBooked(eventId, eventCreationDateTime, hotelRoomId, hotelId, tenantId, days);
    }
}
