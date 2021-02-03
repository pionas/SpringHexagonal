package info.pionas.rental.domain.owner;

public class OwnerDoesNotExistException extends RuntimeException {
    public OwnerDoesNotExistException(String ownerId) {
        super("Owner with id " + ownerId + " does not exist");
    }
}
