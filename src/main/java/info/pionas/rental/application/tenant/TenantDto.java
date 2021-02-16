package info.pionas.rental.application.tenant;

import info.pionas.rental.domain.tenant.NewTenantDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static info.pionas.rental.domain.tenant.NewTenantDto.Builder.newTenantDto;

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
        return newTenantDto()
                .withLogin(getLogin())
                .withEmail(getEmail())
                .withFirstName(getFirstName())
                .withLastName(getLastName())
                .withPassword(getPassword())
                .withPasswordRepeat(getPasswordRepeat())
                .withCurrentPassword(getCurrentPassword())
                .build();
    }
}
