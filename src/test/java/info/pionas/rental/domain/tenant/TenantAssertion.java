package info.pionas.rental.domain.tenant;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class TenantAssertion {
    private final Tenant actual;

    public static TenantAssertion assertThat(Tenant actual) {
        return new TenantAssertion(actual);
    }

    public TenantAssertion hasLoginEqualsTo(String login) {
        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("login", login);
        return this;
    }

    public TenantAssertion hasFirstNameEqualsTo(String firstName) {
        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("firstName", firstName);
        return this;
    }

    public TenantAssertion hasLastNameEqualsTo(String lastName) {
        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("lastName", lastName);
        return this;
    }

    public TenantAssertion hasEmailEqualsTo(String email) {
        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("email", email);
        return this;
    }
}
