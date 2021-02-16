package info.pionas.rental.infrastructure.persistence.jpa.tenant;

import info.pionas.rental.domain.tenant.Tenant;
import info.pionas.rental.domain.tenant.TenantAssertion;
import info.pionas.rental.domain.tenant.TenantNotFoundException;
import info.pionas.rental.domain.tenant.TenantRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static info.pionas.rental.domain.tenant.Tenant.Builder.tenant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Tag("DomainRepositoryIntegrationTest")
class JpaTenantRepositoryIntegrationTest {
    private String LOGIN = "john.doe";
    private String FIRST_NAME = "John";
    private String LAST_NAME = "Doe";
    private String EMAIL = "john.doe@example.com";
    @Autowired
    private TenantRepository tenantRepository;
    @Autowired
    private SpringJpaTenantTestRepository springJpaTenantTestRepository;
    private final List<String> tenantIds = new ArrayList<>();

    @AfterEach
    void deleteTenants() {
        springJpaTenantTestRepository.deleteAll(tenantIds);
    }

    @Test
    void shouldRecognizeWhenTenantDoesNotExist() {
        String nonExistingTenantId = UUID.randomUUID().toString();

        boolean actual = tenantRepository.existById(nonExistingTenantId);

        assertThat(actual).isFalse();
    }

    @Test
    void shouldRecognizeWhenTenantExists() {
        String existingId = givenExistingTenant(createTenant());

        boolean actual = tenantRepository.existById(existingId);

        assertThat(actual).isTrue();
    }

    @Test
    void shouldThrowExceptionWhenTenantDoesNotExist() {
        String nonExistingTenantId = UUID.randomUUID().toString();

        TenantNotFoundException actual = assertThrows(TenantNotFoundException.class, () -> {
            tenantRepository.findById(nonExistingTenantId);
        });

        assertThat(actual).hasMessage("Tenant with id: " + nonExistingTenantId + " does not exist");
    }

    @Test
    @Transactional
    void shouldReturnExistingTenant() {
        String existingId = givenExistingTenant(createTenant());

        Tenant actual = tenantRepository.findById(existingId);

        TenantAssertion.assertThat(actual)
                .hasLoginEqualsTo(LOGIN)
                .hasFirstNameEqualsTo(FIRST_NAME)
                .hasLastNameEqualsTo(LAST_NAME)
                .hasEmailEqualsTo(EMAIL);
    }

    @Test
    @Transactional
    void shouldReturnExistingTenantWeWant() {
        Tenant tenant1 = tenant()
                .withLogin("john.doe")
                .withEmail("john.doe@example.com")
                .withFirstName("John")
                .withLastName("Doe")
                .build();
        givenExistingTenant(tenant1);
        String existingId = givenExistingTenant(createTenant());
        Tenant tenant2 = tenant()
                .withLogin("john.doe2")
                .withEmail("john.doe@example.com")
                .withFirstName("John")
                .withLastName("Doe")
                .build();
        givenExistingTenant(tenant2);
        Tenant tenant3 = tenant()
                .withLogin("john.doe3")
                .withEmail("john.doe@example.com")
                .withFirstName("John")
                .withLastName("Doe")
                .build();
        givenExistingTenant(tenant3);

        Tenant actual = tenantRepository.findById(existingId);

        TenantAssertion.assertThat(actual)
                .hasLoginEqualsTo(LOGIN)
                .hasFirstNameEqualsTo(FIRST_NAME)
                .hasLastNameEqualsTo(LAST_NAME)
                .hasEmailEqualsTo(EMAIL);
    }

    @Test
    void shouldRecognizeWhenTenantByEmailExists() {
        givenExistingTenant(createTenant());

        Tenant actual = tenantRepository.findByEmail(EMAIL).get();

        TenantAssertion.assertThat(actual)
                .hasLoginEqualsTo(LOGIN)
                .hasFirstNameEqualsTo(FIRST_NAME)
                .hasLastNameEqualsTo(LAST_NAME)
                .hasEmailEqualsTo(EMAIL);
    }

    @Test
    void shouldRecognizeWhenTenantByLoginExists() {
        givenExistingTenant(createTenant());

        Tenant actual = tenantRepository.findByLogin(LOGIN).get();

        TenantAssertion.assertThat(actual)
                .hasLoginEqualsTo(LOGIN)
                .hasFirstNameEqualsTo(FIRST_NAME)
                .hasLastNameEqualsTo(LAST_NAME)
                .hasEmailEqualsTo(EMAIL);
    }

    private String givenExistingTenant(Tenant tenant) {
        String tenantId = tenantRepository.save(tenant);
        tenantIds.add(tenantId);

        return tenantId;
    }

    private Tenant createTenant() {
        return tenant()
                .withLogin(LOGIN)
                .withFirstName(FIRST_NAME)
                .withLastName(LAST_NAME)
                .withEmail(EMAIL)
                .build();
    }

}