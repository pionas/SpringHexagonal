package info.pionas.rental.domain.tenant;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static info.pionas.rental.domain.tenant.Tenant.Builder.tenant;

class TenantTest {
    private static final String LOGIN = "john.doe";
    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Doe";
    private static final String EMAIL = "john.doe@example.com";

    @Test
    void shouldRecognizeTheSameInstanceAsTheSameAggregate() {
        Tenant actual = givenTenant();

        Assertions.assertThat(actual.equals(actual)).isTrue();
        Assertions.assertThat(actual.hashCode()).isEqualTo(actual.hashCode());
    }

    @Test
    void shouldRecognizeNullIsNotTheSameAsTenant() {
        Tenant actual = givenTenant();

        Assertions.assertThat(actual.equals(null)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("notTeSameTenants")
    void shouldRecognizeTenantsDoesNotRepresentTheSameAggregate(Object toCompare) {
        Tenant actual = givenTenant();

        Assertions.assertThat(actual.equals(toCompare)).isFalse();
        Assertions.assertThat(actual.hashCode()).isNotEqualTo(toCompare.hashCode());
    }

    private static Stream<Object> notTeSameTenants() {
        return Stream.of(
                getTenantBuilder().withLogin("212121").build(),
                new Object()
        );
    }

    private Tenant givenTenant() {
        return getTenantBuilder()
                .build();
    }

    private static Tenant.Builder getTenantBuilder() {
        return tenant()
                .withLogin(LOGIN)
                .withFirstName(FIRST_NAME)
                .withLastName(LAST_NAME)
                .withEmail(EMAIL);
    }
}