package info.pionas.rental.domain.tenant;

public class TenantException extends RuntimeException {
    TenantException(String message) {
        super(message);
    }

    public static RuntimeException byLogin(String login) {
        return new TenantException("Login " + login + " already exists");
    }

    public static RuntimeException byEmail(String email) {
        return new TenantException("Email " + email + " already exists");
    }

    public static RuntimeException byPassword() {
        return new TenantException("Password not matches");
    }
}
