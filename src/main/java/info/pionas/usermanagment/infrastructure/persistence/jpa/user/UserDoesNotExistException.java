package info.pionas.usermanagment.infrastructure.persistence.jpa.user;

class UserDoesNotExistException extends RuntimeException {
    UserDoesNotExistException(String userId) {
        super("User with id " + userId + " does not exist");
    }
}
