package info.pionas.rental.application.tenant;

import info.pionas.rental.domain.tenant.NewTenantDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TenantDto {
    private String login;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String passwordRepeat;
    private String currentPassword;

    public NewTenantDto asNewTenantDto() {
        return NewTenantDto.builder()
                .login(getLogin())
                .email(getEmail())
                .firstName(getFirstName())
                .lastName(getLastName())
                .password(getPassword())
                .passwordRepeat(getPasswordRepeat())
                .currentPassword(getCurrentPassword())
                .build();
    }
}
