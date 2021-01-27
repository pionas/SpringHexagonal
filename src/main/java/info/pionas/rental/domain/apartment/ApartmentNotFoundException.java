package info.pionas.rental.domain.apartment;

public class ApartmentNotFoundException extends RuntimeException {
    public ApartmentNotFoundException(String apartmentId) {
        super("Apartment with id " + apartmentId + " does not exist");
    }
}
