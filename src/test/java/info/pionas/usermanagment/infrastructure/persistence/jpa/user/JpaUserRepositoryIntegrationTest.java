package info.pionas.usermanagment.infrastructure.persistence.jpa.user;

import info.pionas.usermanagment.domain.user.Name;
import info.pionas.usermanagment.domain.user.User;
import info.pionas.usermanagment.domain.user.UserAssertion;
import info.pionas.usermanagment.domain.user.UserRepository;
import org.assertj.core.api.Assertions;
 import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Tag("DomainRepositoryIntegrationTest")
class JpaUserRepositoryIntegrationTest {
    private static final String LOGIN = "pionas";
    private static final String NAME = "Adrian";
    private static final String LAST_NAME = "Pionka";

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SpringJpaUserTestRepository springJpaUserTestRepository;
    private final List<String> userIds = new ArrayList<>();

    @AfterEach
    void deleteApartments() {
        springJpaUserTestRepository.deleteAll(userIds);
    }


    @Test
    void shouldThrowExceptionWhenUserDoesNotExist() {
        String id = UUID.randomUUID().toString();

        UserDoesNotExistException actual = assertThrows(UserDoesNotExistException.class, () -> userRepository.findById(id));

        Assertions.assertThat(actual).hasMessage("User with id " + id + " does not exist");
    }

    @Test
    void shouldCreateUser() {
        String userId = givenExistingUser(createUser());

        UserAssertion.assertThat(findBy(userId)).represents(LOGIN, NAME, LAST_NAME);
    }

    @Test
    @Transactional
    void shouldReturnExistingUserWeWant() {
        User user1 = new User("john.doe", new Name("John", "Doe"));
        givenExistingUser(user1);
        String existingId = givenExistingUser(createUser());
        User user2 = new User("john.doe2", new Name("John", "Doe"));
        givenExistingUser(user2);
        User user3 = new User("john.doe3", new Name("John", "Doe"));
        givenExistingUser(user3);

        UserAssertion.assertThat(findBy(existingId)).represents(LOGIN, NAME, LAST_NAME);
    }

    private User createUser() {
        return new User(LOGIN, new Name(NAME, LAST_NAME));
    }

    private String givenExistingUser(User user) {
        String userId = userRepository.save(user);
        userIds.add(userId);
        return userId;
    }

    private User findBy(String userId) {
        return userRepository.findById(userId);
    }
}