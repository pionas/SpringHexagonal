package info.pionas.rental.domain.booking;

public class NotAllowedBookingStatusTransitionException extends RuntimeException {
    public NotAllowedBookingStatusTransitionException(BookingStatus from, BookingStatus to) {
        super("Not allowed transition from " + from + " to " + to + " booking");
    }
}
