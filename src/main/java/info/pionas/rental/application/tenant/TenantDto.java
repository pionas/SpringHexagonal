package info.pionas.rental.application.tenant;

import info.pionas.rental.domain.tenant.NewTenantDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import static info.pionas.rental.domain.tenant.NewTenantDto.Builder.newTenantDto;

@AllArgsConstructor
@Getter
public class TenantDto {

    @SuppressWarnings("checkstyle:MagicNumber")
    @Size(min = 3, max = 250, message = "Login size must be between 3 and 250")
    private String login;
    @Email(message = "Email must be a well-formed address")
    private String email;
    @NotEmpty(message = "Please provide a first name")
    private String firstName;
    @NotEmpty(message = "Please provide a last name")
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
