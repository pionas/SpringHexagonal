package info.pionas.rental.domain.hotelroom;

import java.time.LocalDate;
import java.util.List;

public class HotelRoomBookedTestFactory {
    public static HotelRoomBooked create(String hotelRoomId, String hotelId, String tenantId, List<LocalDate> days) {
        return HotelRoomBooked.create(hotelRoomId, hotelId, tenantId, days);
    }
}