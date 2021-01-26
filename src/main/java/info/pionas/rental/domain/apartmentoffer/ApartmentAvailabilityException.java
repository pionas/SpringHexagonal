package info.pionas.rental.domain.apartmentoffer;

public class ApartmentAvailabilityException extends RuntimeException {
    ApartmentAvailabilityException() {
        super("Start date of availability is after end date");
    }
}
