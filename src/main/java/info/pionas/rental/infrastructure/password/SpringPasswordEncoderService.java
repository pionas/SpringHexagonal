package info.pionas.rental.infrastructure.password;

import info.pionas.rental.domain.password.PasswordEncoderFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SpringPasswordEncoderService implements PasswordEncoderFactory {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    @Override
    public String encode(CharSequence var) {
        return passwordEncoder.encode(var);
    }

    @Override
    public boolean matches(CharSequence var1, String var2) {
        return passwordEncoder.matches(var1, var2);
    }
}
