package info.pionas.rental.domain.hotelbookinghistory;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Adi
 */
@RequiredArgsConstructor
//@Entity
class HotelRoomBooking {

    private final LocalDateTime bookingDateTime;
    private final String tenantId;
    private final List<LocalDate> days;

    public static HotelRoomBooking start(LocalDateTime bookingDateTime, String tenantId, List<LocalDate> days) {
        return new HotelRoomBooking(bookingDateTime, tenantId, days);
    }
}
