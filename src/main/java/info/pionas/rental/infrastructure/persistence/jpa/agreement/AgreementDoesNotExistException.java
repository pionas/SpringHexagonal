package info.pionas.rental.infrastructure.persistence.jpa.agreement;

public class AgreementDoesNotExistException extends RuntimeException {
    public AgreementDoesNotExistException(String id) {
        super("Agreement with id " + id + " does not exist");
    }
}
