package info.pionas.rental.domain.hotelroomoffer;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
class HotelRoomAvailability {
    private LocalDate start;
    private LocalDate end;

    static HotelRoomAvailability of(LocalDate start, LocalDate end) {
        if (start.isAfter(end)) {
            throw new HotelRoomAvailabilityException(start, end);
        }
        if (start.isBefore(LocalDate.now())) {
            throw new HotelRoomAvailabilityException(start);
        }
        return new HotelRoomAvailability(start, end);
    }
}
