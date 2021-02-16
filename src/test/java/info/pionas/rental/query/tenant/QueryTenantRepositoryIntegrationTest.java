package info.pionas.rental.query.tenant;

import info.pionas.rental.domain.tenant.Tenant;
import info.pionas.rental.domain.tenant.TenantRepository;
import info.pionas.rental.infrastructure.persistence.jpa.tenant.SpringJpaTenantTestRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.UUID;

import static info.pionas.rental.domain.tenant.Tenant.Builder.tenant;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Tag("IntegrationTest")
class QueryTenantRepositoryIntegrationTest {
    private static final String LOGIN_1 = "john.doe";
    private static final String FIRST_NAME_1 = "John";
    private static final String LAST_NAME_1 = "Doe";
    private static final String EMAIL_1 = "john.doe@example.com";
    private static final String LOGIN_2 = "jane.doe";
    private static final String FIRST_NAME_2 = "Jane";
    private static final String LAST_NAME_2 = "Doe";
    private static final String EMAIL_2 = "jane.doe@example.com";
    private static final String PASSWORD = "123456";
    @Autowired
    private QueryTenantRepository queryTenantRepository;
    @Autowired
    private TenantRepository tenantRepository;
    @Autowired
    private SpringJpaTenantTestRepository springJpaTenantTestRepository;
    private String tenantId1;
    private String tenantId2;

    @BeforeEach
    void givenTenants() {
        Tenant tenant1 = tenant()
                .withLogin(LOGIN_1)
                .withFirstName(FIRST_NAME_1)
                .withLastName(LAST_NAME_1)
                .withEmail(EMAIL_1)
                .build();
        tenantId1 = tenantRepository.save(tenant1);

        Tenant tenant2 = tenant()
                .withLogin(LOGIN_2)
                .withFirstName(FIRST_NAME_2)
                .withLastName(LAST_NAME_2)
                .withEmail(EMAIL_2)
                .build();
        tenantId2 = tenantRepository.save(tenant2);

        queryTenantRepository.findAll();
    }

    @AfterEach
    void deleteTenants() {
        springJpaTenantTestRepository.deleteAll(asList(tenantId1, tenantId2));
    }

    @Test
    @Transactional
    void shouldFindAllTenants() {
        Iterable<TenantReadModel> actual = queryTenantRepository.findAll();

        assertThat(actual)
                .hasSize(2)
                .anySatisfy(tenantReadModel -> {
                    TenantReadModelAssertion.assertThat(tenantReadModel)
                            .hasTenantId()
                            .hasLoginEqualsTo(LOGIN_1)
                            .hasFirstNameEqualsTo(FIRST_NAME_1)
                            .hasLastNameEqualsTo(LAST_NAME_1)
                            .hasEmailEqualsTo(EMAIL_1);
                })
                .anySatisfy(tenantReadModel -> {
                    TenantReadModelAssertion.assertThat(tenantReadModel)
                            .hasTenantId()
                            .hasLoginEqualsTo(LOGIN_2)
                            .hasFirstNameEqualsTo(FIRST_NAME_2)
                            .hasLastNameEqualsTo(LAST_NAME_2)
                            .hasEmailEqualsTo(EMAIL_2);
                });
    }

    @Test
    void shouldReturnNotExistingTenant() {
        TenantReadModel actual = queryTenantRepository.findById(UUID.randomUUID().toString());
        Assertions.assertThat(actual).isNull();
    }

}