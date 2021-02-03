package info.pionas.usermanagment.domain.user;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;

import static info.pionas.usermanagment.domain.user.User.Builder.user;

@RequiredArgsConstructor
public class UserAssertion {
    private final User actual;

    public static UserAssertion assertThat(User actual) {
        return new UserAssertion(actual);
    }

    public UserAssertion represents(String login, String name, String lastName) {
        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("login", login);
        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("name.name", name);
        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("name.lastName", lastName);
        User expected = user().withLogin(login).withName(name, lastName).build();
        Assertions.assertThat(actual).isEqualTo(expected);
        return this;
    }
}
