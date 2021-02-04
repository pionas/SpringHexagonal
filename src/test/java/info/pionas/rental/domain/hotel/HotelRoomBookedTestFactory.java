package info.pionas.rental.domain.hotel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class HotelRoomBookedTestFactory {
    public static HotelRoomBooked create(
            String eventId, LocalDateTime eventCreationDateTime, String hotelId, int hotelRoomNumber, String tenantId, List<LocalDate> days) {
        return new HotelRoomBooked(eventId, eventCreationDateTime, hotelRoomNumber, hotelId, tenantId, days);
    }
}