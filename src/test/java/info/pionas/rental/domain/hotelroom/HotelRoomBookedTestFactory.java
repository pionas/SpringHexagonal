package info.pionas.rental.domain.hotelroom;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class HotelRoomBookedTestFactory {
    public static HotelRoomBooked create(String eventId, LocalDateTime eventCreationDateTime, String hotelRoomId, String hotelId, String tenantId, List<LocalDate> days) {
        return new HotelRoomBooked(eventId, eventCreationDateTime, hotelRoomId, hotelId, tenantId, days);
    }
}