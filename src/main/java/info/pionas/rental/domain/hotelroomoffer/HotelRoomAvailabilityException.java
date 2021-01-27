package info.pionas.rental.domain.hotelroomoffer;

import java.time.LocalDate;

public class HotelRoomAvailabilityException extends RuntimeException {

    private HotelRoomAvailabilityException(String message) {
        super(message);
    }

    static RuntimeException startAfterEnd(LocalDate start, LocalDate end) {
        return new HotelRoomAvailabilityException("Start date: " + start + " of availability is after end date: " + end);
    }

    static RuntimeException startFromPast(LocalDate start) {
        return new HotelRoomAvailabilityException("Start date: " + start + " is past date.");
    }
}
