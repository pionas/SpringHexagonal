package info.pionas.rental.infrastructure.persistence.jpa.booking;

public class BookingDoesNotExistException extends RuntimeException {
    BookingDoesNotExistException(String id) {
        super("Booking with id " + id + " does not exist");
    }
}
