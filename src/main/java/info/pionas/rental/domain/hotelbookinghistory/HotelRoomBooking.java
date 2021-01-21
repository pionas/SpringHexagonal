package info.pionas.rental.domain.hotelbookinghistory;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @author Adi
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
class HotelRoomBooking {
    @Id
    @GeneratedValue
    private UUID id;
    private LocalDateTime bookingDateTime;
    private String tenantId;
    @ElementCollection
    private List<LocalDate> days;

    HotelRoomBooking(LocalDateTime bookingDateTime, String tenantId, List<LocalDate> days) {
        this.bookingDateTime = bookingDateTime;
        this.tenantId = tenantId;
        this.days = days;
    }

    public static HotelRoomBooking start(LocalDateTime bookingDateTime, String tenantId, List<LocalDate> days) {
        return new HotelRoomBooking(bookingDateTime, tenantId, days);
    }
}
