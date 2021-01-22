package info.pionas.rental.domain.hotelbookinghistory;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class HotelRoomBookingHistory {

    @Id
    private String hotelRoomId;

    @OneToMany(cascade = CascadeType.ALL)
    private List<HotelRoomBooking> bookings = new ArrayList<>();

    public HotelRoomBookingHistory(String hotelRoomId) {
        this.hotelRoomId = hotelRoomId;
    }

    boolean hasIdEqualTo(String hotelRoomId) {
        return this.hotelRoomId.equals(hotelRoomId);
    }

    void add(LocalDateTime bookingDateTime, String tenantId, List<LocalDate> days) {
        bookings.add(new HotelRoomBooking(bookingDateTime, tenantId, days));
    }

}
