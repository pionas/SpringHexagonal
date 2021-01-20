package info.pionas.rental.infrastructure.persistence.jpa.apartment;

class ApartmentDoesNotExistException extends RuntimeException {
    ApartmentDoesNotExistException(String id) {
        super("Apartment with id " + id + " does not exist");
    }
}
