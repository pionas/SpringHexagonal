package info.pionas.rental.infrastructure.persistence.jpa.hotel;

public class HotelDoesNotExistException extends RuntimeException {
    HotelDoesNotExistException(String id) {
        super("Hotel with id " + id + " does not exist");
    }
}
