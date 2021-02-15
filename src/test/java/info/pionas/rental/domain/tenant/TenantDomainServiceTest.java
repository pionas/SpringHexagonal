package info.pionas.rental.domain.tenant;

import info.pionas.rental.domain.error.ErrorExceptions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static info.pionas.rental.domain.tenant.Tenant.Builder.tenant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

class TenantDomainServiceTest {
    private static final String LOGIN_1 = "john.doe";
    private static final String FIRST_NAME_1 = "John";
    private static final String LAST_NAME_1 = "Doe";
    private static final String EMAIL_1 = "john.doe@example.com";
    private static final String LOGIN_2 = "jane.doe";
    private static final String FIRST_NAME_2 = "Jane";
    private static final String LAST_NAME_2 = "Doe";
    private static final String EMAIL_2 = "jane.doe@example.com";
    private static final String PASSWORD = "123456";
    private static final String ID = UUID.randomUUID().toString();

    private final PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
    private final TenantRepository tenantRepository = mock(TenantRepository.class);

    private final TenantDomainService service = new TenantDomainService(tenantRepository, passwordEncoder);

    @Test
    void shouldCreateTenant() {
        givenNoExistingTenant();
        Tenant actual = service.create(givenNewTenantDto(PASSWORD, null));

        TenantAssertion.assertThat(actual)
                .hasLoginEqualsTo(LOGIN_1)
                .hasFirstNameEqualsTo(FIRST_NAME_1)
                .hasLastNameEqualsTo(LAST_NAME_1)
                .hasEmailEqualsTo(EMAIL_1);
    }

    @Test
    void shouldRecognizeTenantLoginExist() {
        passwordMatches();
        givenExistingTenantByLogin();

        ErrorExceptions actual = assertThrows(ErrorExceptions.class, () -> service.create(givenNewTenantDto(PASSWORD, null)));

        assertThat(actual).hasMessage("Login " + LOGIN_1 + " already exist");
    }

    @Test
    void shouldRecognizeTenantEmailExist() {
        givenExistingTenantByEmail();

        ErrorExceptions actual = assertThrows(ErrorExceptions.class, () -> service.create(givenNewTenantDto(PASSWORD, null)));

        assertThat(actual).hasMessage("Email " + EMAIL_1 + " already exist");
    }

    @Test
    void shouldRecognizeTenantInvalidPassword() {
        givenNoExistingTenant();
        NewTenantDto tenantDto = givenNewTenantDto("ds", null);
        TenantException actual = assertThrows(TenantException.class, () -> service.create(tenantDto));

        assertThat(actual).hasMessage("Password not matches");
    }

    @Test
    void shouldUpdateTenant() {
        ArgumentCaptor<Tenant> captor = ArgumentCaptor.forClass(Tenant.class);
        givenExistingTenant();
        passwordMatches();
        NewTenantDto dto = getUpdateTenant(LOGIN_1, EMAIL_1, FIRST_NAME_2, LAST_NAME_2, PASSWORD, PASSWORD, "123456");
        service.update(ID, dto);

        then(tenantRepository).should().save(captor.capture());

        TenantAssertion.assertThat(captor.getValue())
                .hasLoginEqualsTo(LOGIN_1)
                .hasFirstNameEqualsTo(FIRST_NAME_2)
                .hasLastNameEqualsTo(LAST_NAME_2)
                .hasEmailEqualsTo(EMAIL_1);
    }

    @Test
    void shouldRecognizeTenantLoginExistWhenTryChangeLogin() {
        givenExistingTenantByLogin();
        given(tenantRepository.findById(ID)).willReturn(givenTenant2());
        NewTenantDto dto = getUpdateTenant(LOGIN_1, EMAIL_2, FIRST_NAME_2, LAST_NAME_2, PASSWORD, PASSWORD, "123456");
        ErrorExceptions actual = assertThrows(ErrorExceptions.class, () -> service.update(ID, dto));

        assertThat(actual).hasMessage("Login " + LOGIN_1 + " already exist");
    }

    @Test
    void shouldRecognizeTenantEmailExistWhenTryChangeEmail() {
        givenExistingTenantByEmail();
        given(tenantRepository.findById(ID)).willReturn(givenTenant2());

        NewTenantDto dto = getUpdateTenant(LOGIN_2, EMAIL_1, FIRST_NAME_2, LAST_NAME_2, PASSWORD, PASSWORD, "123456");
        ErrorExceptions actual = assertThrows(ErrorExceptions.class, () -> service.update(ID, dto));

        assertThat(actual).hasMessage("Email " + EMAIL_1 + " already exist");
    }

    @Test
    void shouldRecognizeTenantInvalidPasswordWhenTryChangePassword() {
        givenExistingTenant();
        passwordNoMatches();
        NewTenantDto dto = getUpdateTenant(LOGIN_2, EMAIL_2, FIRST_NAME_2, LAST_NAME_2, PASSWORD, "sas", "123456");
        TenantException actual = assertThrows(TenantException.class, () -> service.update(ID, dto));

        assertThat(actual).hasMessage("Password not matches");
    }


    @Test
    void shouldRecognizeTenantInvalidCurrentPassword() {
        givenExistingTenant();
        passwordNoMatches();
        NewTenantDto dto = getUpdateTenant(LOGIN_1, EMAIL_1, FIRST_NAME_1, LAST_NAME_1, PASSWORD, PASSWORD, "sda");
        TenantException actual = assertThrows(TenantException.class, () -> service.update(ID, dto));

        assertThat(actual).hasMessage("Password not matches");
    }

    private void givenExistingTenant() {
        given(tenantRepository.findById(ID)).willReturn(givenTenant());
    }

    private void passwordMatches() {
        given(passwordEncoder.encode(any())).willReturn(PASSWORD);
        given(passwordEncoder.matches(any(), any())).willReturn(true);
    }

    private void passwordNoMatches() {
        given(passwordEncoder.matches(any(), any())).willReturn(false);
    }

    private void givenNoExistingTenant() {
        givenNoExistingTenantByEmail();
        givenNoExistingTenantByLogin();
    }

    private void givenNoExistingTenantByEmail() {
        given(tenantRepository.findByEmail(EMAIL_1)).willReturn(Optional.empty());
    }

    private void givenNoExistingTenantByLogin() {
        given(tenantRepository.findByLogin(LOGIN_1)).willReturn(Optional.empty());
    }

    private void givenExistingTenantByEmail() {
        given(tenantRepository.findByEmail(EMAIL_1)).willReturn(Optional.of(givenTenant()));
    }

    private void givenExistingTenantByLogin() {
        given(tenantRepository.findByLogin(LOGIN_1)).willReturn(Optional.of(givenTenant()));
    }

    private Tenant givenTenant() {
        return tenant()
                .withLogin(LOGIN_1)
                .withFirstName(FIRST_NAME_1)
                .withLastName(LAST_NAME_1)
                .withEmail(EMAIL_1)
                .build();
    }

    private Tenant givenTenant2() {
        return tenant()
                .withLogin(LOGIN_2)
                .withFirstName(FIRST_NAME_2)
                .withLastName(LAST_NAME_2)
                .withEmail(EMAIL_2)
                .build();
    }

    private NewTenantDto givenNewTenantDto(String passwordRepeat, String currentPassword) {
        return getUpdateTenant(LOGIN_1, EMAIL_1, FIRST_NAME_1, LAST_NAME_1, PASSWORD, passwordRepeat, currentPassword);
    }


    private NewTenantDto getUpdateTenant(String login, String email, String firstName, String lastName, String password, String passwordRepeat, String currentPassword) {
        return new NewTenantDto(login, email, firstName, lastName, password, passwordRepeat, currentPassword);
    }


}