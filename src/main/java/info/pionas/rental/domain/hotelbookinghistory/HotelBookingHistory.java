package info.pionas.rental.domain.hotelbookinghistory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author Adi
 */
@RequiredArgsConstructor
//@Entity
public class HotelBookingHistory {

    @Id
    private final String hotelId;

    @OneToMany
    private final List<HotelRoomBookingHistory> hotelRoomBookingHistories = new ArrayList<>();

    public void add(String hotelRoomId, LocalDateTime bookingDateTime, String tenantId, List<LocalDate> days) {
        HotelRoomBookingHistory hotelRoomBookingHistory = findFor(hotelRoomId);
        hotelRoomBookingHistory.add(bookingDateTime, tenantId, days);

    }

    private HotelRoomBookingHistory findFor(String hotelRoomId) {
        Optional<HotelRoomBookingHistory> history = hotelRoomBookingHistories.stream()
                .filter(hotelRoomBookingHistory -> hotelRoomBookingHistory.hasIdEqualTo(hotelRoomId))
                .findFirst();
        if (history.isEmpty()) {
            HotelRoomBookingHistory hotelRoomBookingHistory = new HotelRoomBookingHistory(hotelRoomId);
            hotelRoomBookingHistories.add(hotelRoomBookingHistory);
            return hotelRoomBookingHistory;
        }
        return history.get();
    }

}
