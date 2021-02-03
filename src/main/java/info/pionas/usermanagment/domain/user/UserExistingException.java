package info.pionas.usermanagment.domain.user;

public class UserExistingException extends RuntimeException {
    UserExistingException(String login) {
        super("User with login " + login + " already exists");
    }
}
