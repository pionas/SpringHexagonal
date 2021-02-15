package info.pionas.rental.domain.tenant;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@Builder
public class NewTenantDto {
    private final String login;
    private final String email;
    private final String firstName;
    private final String lastName;
    private final String password;
    private final String passwordRepeat;
    private final String currentPassword;

}