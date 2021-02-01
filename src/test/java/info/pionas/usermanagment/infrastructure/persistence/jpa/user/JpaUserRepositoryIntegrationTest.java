package info.pionas.usermanagment.infrastructure.persistence.jpa.user;

import info.pionas.rental.domain.hotelbookinghistory.HotelBookingHistory;
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

import static info.pionas.usermanagment.domain.user.User.Builder.user;
import static org.assertj.core.api.Assertions.assertThat;
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
        User user1 = user().withLogin("john.doe").withName("John", "Doe").build();
        givenExistingUser(user1);
        String existingId = givenExistingUser(createUser());
        User user2 = user().withLogin("john.doe2").withName("John", "Doe").build();
        givenExistingUser(user2);
        User user3 = user().withLogin("john.doe3").withName("John", "Doe").build();
        givenExistingUser(user3);

        UserAssertion.assertThat(findBy(existingId)).represents(LOGIN, NAME, LAST_NAME);
    }

    @Test
    void shouldRecognizeUserDoesNotExist() {
        assertThat(userRepository.existWithLogin("JOHN")).isFalse();
    }

    @Test
    void shouldRecognizeUserExists() {
        givenExistingUser(createUser());
        assertThat(userRepository.existWithLogin(LOGIN)).isTrue();
    }

    private User createUser() {
        return user().withLogin(LOGIN).withName(NAME, LAST_NAME).build();
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