package info.pionas.usermanagment.infrastructure.persistence.jpa.user;

import info.pionas.usermanagment.domain.user.Name;
import info.pionas.usermanagment.domain.user.User;
import info.pionas.usermanagment.domain.user.UserAssertion;
import info.pionas.usermanagment.domain.user.UserRepository;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Tag("DomainRepositoryIntegrationTest")
class JpaUserRepositoryIntegrationTest {
    private static final String LOGIN = "pionas";
    private static final String NAME = "Adrian";
    private static final String LAST_NAME = "Pionka";

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldCreateUser() {
        String userId = givenExistingUser(createUser());

        UserAssertion.assertThat(findBy(userId)).represents(LOGIN, NAME, LAST_NAME);
    }

    private User createUser() {
        return new User(LOGIN, new Name(NAME, LAST_NAME));
    }

    private String givenExistingUser(User user) {
        return userRepository.save(user);
    }

    private User findBy(String userId) {
        return userRepository.findById(userId);
    }
}