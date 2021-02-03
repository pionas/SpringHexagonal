package info.pionas.rental.domain.apartment;

public class ApartmentBookingException extends RuntimeException {
    public ApartmentBookingException() {
        super("There are accepted bookings in given period");
    }
}
