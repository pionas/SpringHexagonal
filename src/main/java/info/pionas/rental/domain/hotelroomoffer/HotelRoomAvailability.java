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

    static HotelRoomAvailability from(LocalDate start, LocalDate end) {
        if (start.isAfter(end)) {
            throw HotelRoomAvailabilityException.startAfterEnd(start, end);
        }
        if (start.isBefore(LocalDate.now())) {
            throw HotelRoomAvailabilityException.startFromPast(start);
        }
        return new HotelRoomAvailability(start, end);
    }

    static HotelRoomAvailability fromStart(LocalDate start) {
        return new HotelRoomAvailability(start, start.plusYears(1));
    }
}
