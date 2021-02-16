package info.pionas.rental.query.tenant;

import info.pionas.rental.domain.tenant.Tenant;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class TenantReadModelAssertion {
    private final TenantReadModel actual;

    public static TenantReadModelAssertion assertThat(TenantReadModel actual) {
        return new TenantReadModelAssertion(actual);
    }

    public TenantReadModelAssertion hasLoginEqualsTo(String login) {
        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("login", login);
        return this;
    }

    public TenantReadModelAssertion hasFirstNameEqualsTo(String firstName) {
        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("firstName", firstName);
        return this;
    }

    public TenantReadModelAssertion hasLastNameEqualsTo(String lastName) {
        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("lastName", lastName);
        return this;
    }

    public TenantReadModelAssertion hasEmailEqualsTo(String email) {
        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("email", email);
        return this;
    }
}
