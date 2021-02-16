package info.pionas.rental.application.tenant;

import info.pionas.rental.domain.error.ErrorExceptions;
import info.pionas.rental.domain.password.PasswordEncoderFactory;
import info.pionas.rental.domain.tenant.Tenant;
import info.pionas.rental.domain.tenant.TenantAssertion;
import info.pionas.rental.domain.tenant.TenantRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;
import java.util.UUID;

import static info.pionas.rental.domain.tenant.Tenant.Builder.tenant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;

class TenantApplicationServiceTest {
    private static final String ID = UUID.randomUUID().toString();
    private static final String LOGIN = "john.doe";
    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Doe";
    private static final String EMAIL = "john.doe@example.com";
    private static final String PASSWORD = "123456";

    private final PasswordEncoderFactory passwordEncoder = mock(PasswordEncoderFactory.class);
    private final TenantRepository tenantRepository = mock(TenantRepository.class);
    private final TenantApplicationService service = new TenantApplicationServiceFactory().tenantApplicationService(tenantRepository, passwordEncoder);

    @Test
    void shouldAddNewTenant() {
        ArgumentCaptor<Tenant> captor = ArgumentCaptor.forClass(Tenant.class);

        service.add(givenTenantDto());

        then(tenantRepository).should().save(captor.capture());
        TenantAssertion.assertThat(captor.getValue())
                .hasLoginEqualsTo(LOGIN)
                .hasFirstNameEqualsTo(FIRST_NAME)
                .hasLastNameEqualsTo(LAST_NAME)
                .hasEmailEqualsTo(EMAIL);
    }

    @Test
    void shouldRecognizeTenantDoesNotExist() {
        givenLoginExist();

        ErrorExceptions actual = assertThrows(ErrorExceptions.class, () -> service.add(givenTenantDto()));

        assertThat(actual).hasMessage("Login " + LOGIN + " already exist");
        then(tenantRepository).should(never()).save(any());
    }

    @Test
    void shouldUpdateTenant() {
        ArgumentCaptor<Tenant> captor = ArgumentCaptor.forClass(Tenant.class);
        givenExistingTenant();
        passwordMatches();
        TenantDto dto = new TenantDto(LOGIN, EMAIL, FIRST_NAME, LAST_NAME, PASSWORD, PASSWORD, "123456");
        service.update(ID, dto);

        then(tenantRepository).should().save(captor.capture());

        TenantAssertion.assertThat(captor.getValue())
                .hasLoginEqualsTo(LOGIN)
                .hasFirstNameEqualsTo(FIRST_NAME)
                .hasLastNameEqualsTo(LAST_NAME)
                .hasEmailEqualsTo(EMAIL);
    }

    private void givenExistingTenant() {
        given(tenantRepository.findById(ID)).willReturn(givenTenant());
    }

    private void givenLoginExist() {
        given(tenantRepository.findByLogin(LOGIN)).willReturn(Optional.of(givenTenant()));
    }

    private TenantDto givenTenantDto() {
        return new TenantDto(LOGIN, EMAIL, FIRST_NAME, LAST_NAME, PASSWORD, PASSWORD, null);
    }

    private Tenant givenTenant() {
        return tenant()
                .withLogin(LOGIN)
                .withFirstName(FIRST_NAME)
                .withLastName(LAST_NAME)
                .withEmail(EMAIL)
                .build();
    }

    private void passwordMatches() {
        given(passwordEncoder.encode(any())).willReturn(PASSWORD);
        given(passwordEncoder.matches(any(), any())).willReturn(true);
    }
}