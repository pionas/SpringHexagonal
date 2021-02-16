package info.pionas.rental.domain.password;

public interface PasswordEncoderFactory {
    String encode(CharSequence var1);

    boolean matches(CharSequence var1, String var2);
}
