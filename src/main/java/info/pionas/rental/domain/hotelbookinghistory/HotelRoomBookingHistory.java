package info.pionas.rental.domain.hotelbookinghistory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author Adi
 */
@RequiredArgsConstructor
class HotelRoomBookingHistory {

    @Id
    private final String hotelRoomId;

    @OneToMany
    private final List<HotelRoomBooking> bookings = new ArrayList<>();

    boolean hasIdEqualTo(String hotelRoomId) {
        return this.hotelRoomId.equals(hotelRoomId);
    }

    void add(LocalDateTime bookingDateTime, String tenantId, List<LocalDate> days) {
        bookings.add(new HotelRoomBooking(bookingDateTime, tenantId, days));
    }

}
