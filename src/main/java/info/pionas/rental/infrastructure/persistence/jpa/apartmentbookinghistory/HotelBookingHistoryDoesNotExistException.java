package info.pionas.rental.infrastructure.persistence.jpa.apartmentbookinghistory;

public class HotelBookingHistoryDoesNotExistException extends RuntimeException {
    HotelBookingHistoryDoesNotExistException(String id) {
        super("Hotel Booking History with id " + id + " does not exist");
    }
}
