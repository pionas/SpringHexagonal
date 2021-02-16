package info.pionas.rental.domain.tenant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class NewTenantDto {
    private final String login;
    private final String email;
    private final String firstName;
    private final String lastName;
    private final String password;
    private final String passwordRepeat;
    private final String currentPassword;

    public static class Builder {
        private String login;
        private String email;
        private String firstName;
        private String lastName;
        private String password;
        private String passwordRepeat;
        private String currentPassword;

        private Builder() {
        }

        public static Builder newTenantDto() {
            return new Builder();
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

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder withPasswordRepeat(String passwordRepeat) {
            this.passwordRepeat = passwordRepeat;
            return this;
        }

        public Builder withCurrentPassword(String currentPassword) {
            this.currentPassword = currentPassword;
            return this;
        }

        public NewTenantDto build() {
            return new NewTenantDto(login, email, firstName, lastName, password, passwordRepeat, currentPassword);
        }
    }
}
