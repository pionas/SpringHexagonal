package info.pionas.usermanagment.domain.user;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static info.pionas.usermanagment.domain.user.User.Builder.user;
import static org.assertj.core.api.Assertions.assertThat;

class UserTest {
    private static final String LOGIN_1 = "pionas";
    private static final String NAME_1 = "Adrian";
    private static final String LAST_NAME_1 = "Pionka";

    @Test
    void shouldCreateUserWithAllRequiredFields() {
        User actual = createUser1();

        UserAssertion.assertThat(actual).represents(LOGIN_1, NAME_1, LAST_NAME_1);
    }

    @Test
    void shouldRecognizeTheSameInstanceAsTheSameAggregate() {
        User actual = createUser1();

        Assertions.assertThat(actual.equals(actual)).isTrue();
    }

    @Test
    void shouldRecognizeNullIsNotTheSameAsUser() {
        User actual = createUser1();

        assertThat(actual.equals(null)).isFalse();
        Assertions.assertThat(actual.equals(new Name())).isFalse();
    }

    @Test
    void shouldRecognizeTwoUserInstancesRepresentTheSameAggregate() {
        User toCompare = createUser1();
        User actual = createUser1();
        assertThat(actual.equals(toCompare)).isTrue();
        assertThat(actual.hashCode()).isEqualTo(toCompare.hashCode());
    }

    private User createUser1() {
        return user()
                .withLogin(LOGIN_1)
                .withName(NAME_1, LAST_NAME_1)
                .build();
    }
}