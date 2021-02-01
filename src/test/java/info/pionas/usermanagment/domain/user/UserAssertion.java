package info.pionas.usermanagment.domain.user;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;

@RequiredArgsConstructor
public class UserAssertion {
    private final User actual;

    public static UserAssertion assertThat(User actual) {
        return new UserAssertion(actual);
    }

    public UserAssertion represents(String login, String name, String lastName) {
        User expected = new User(login, new Name(name, lastName));
        Assertions.assertThat(actual).isEqualTo(expected);
        return this;
    }
}
