package info.pionas.rental.domain.tenant;

import info.pionas.common.StringUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "TENANT")
@SuppressWarnings("PMD.UnusedPrivateField")
public class Tenant {
    @Id
    @GeneratedValue
    private UUID id;

    private String login;

    private String email;

    private String firstName;

    private String lastName;

    private String password;

    private String salt;

    private Tenant(String login, String email, String firstName, String lastName, String salt, String password) {
        this.login = login;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salt = salt;
        this.password = password;
    }

    public String id() {
        return id.toString();
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    public void update(PasswordEncoder passwordEncoder, NewTenantDto newTenantDto) {
        if (StringUtils.isNoneEmpty(newTenantDto.getEmail())) {
            setEmail(newTenantDto.getEmail());
        }
        if (StringUtils.isNoneEmpty(newTenantDto.getFirstName())) {
            setFirstName(newTenantDto.getFirstName());
        }
        if (StringUtils.isNoneEmpty(newTenantDto.getLastName())) {
            setLastName(newTenantDto.getLastName());
        }
        if (StringUtils.isNoneEmpty(newTenantDto.getLogin())) {
            setLogin(newTenantDto.getLogin());
        }
        if (StringUtils.isNoneEmpty(newTenantDto.getPassword())) {
            verifyPassword(passwordEncoder, newTenantDto);
            setSalt(StringUtils.generateString(10));
            setPassword(encodePassword(passwordEncoder, getSalt(), newTenantDto.getPassword()));
        }
    }

    private void verifyPassword(PasswordEncoder passwordEncoder, NewTenantDto newTenantDto) {
        boolean isPasswordConfirmValid = isPasswordConfirmValid(newTenantDto.getPassword(), newTenantDto.getPasswordRepeat());
        if (!isPasswordConfirmValid) {
            throw TenantException.byPassword();
        }
        boolean passwordMatches = passwordEncoder.matches(newTenantDto.getCurrentPassword(), getPassword());
        if (!passwordMatches) {
            throw TenantException.byPassword();
        }
    }

    private static boolean isPasswordConfirmValid(String password, String passwordRepeat) {
        return Objects.equals(password, passwordRepeat);
    }

    private static String encodePassword(PasswordEncoder passwordEncoder, String salt, String password) {
        return passwordEncoder.encode(salt + password);
    }

    public static class Builder {
        private PasswordEncoder passwordEncoder;
        private String login;
        private String email;
        private String firstName;
        private String lastName;
        private String password;
        private String salt;

        private Builder() {
        }

        public static Builder tenant() {
            return new Builder();
        }

        public Builder withPasswordEncoder(PasswordEncoder passwordEncoder) {
            this.passwordEncoder = passwordEncoder;
            return this;
        }

        public Builder withLogin(String login) {
            this.login = login;
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        @SuppressWarnings("checkstyle:MagicNumber")
        public Builder withPassword(String password, String passwordRepeat) {
            boolean isPasswordConfirmValid = isPasswordConfirmValid(password, passwordRepeat);
            if (!isPasswordConfirmValid) {
                throw TenantException.byPassword();
            }
            this.salt = StringUtils.generateString(10);
            this.password = encodePassword(passwordEncoder, salt, password);
            return this;
        }

        public Tenant build() {
            return new Tenant(login, email, firstName, lastName, salt, password);
        }

    }

}
