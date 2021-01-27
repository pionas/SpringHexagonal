package info.pionas.rental.domain.hotelroomoffer;

import java.time.LocalDate;

public class HotelRoomAvailabilityException extends RuntimeException {
    public HotelRoomAvailabilityException(LocalDate start, LocalDate end) {
        super("Start date: " + start + " of availability is after end date: " + end);
    }
}
