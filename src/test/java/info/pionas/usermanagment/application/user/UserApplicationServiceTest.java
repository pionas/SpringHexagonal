package info.pionas.usermanagment.application.user;

import info.pionas.usermanagment.domain.user.User;
import info.pionas.usermanagment.domain.user.UserAssertion;
import info.pionas.usermanagment.domain.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

public class UserApplicationServiceTest {
    private static final String LOGIN = "pionas";
    private static final String NAME = "Adrian";
    private static final String LAST_NAME = "Pionka";
    private final UserRepository userRepository = mock(UserRepository.class);
    private final UserApplicationService service = new UserApplicationService(userRepository);

    @Test
    void shouldRegisterUser() {
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        UserDto dto = new UserDto(LOGIN, NAME, LAST_NAME);
        service.register(dto);

        then(userRepository).should().save(captor.capture());
        UserAssertion.assertThat(captor.getValue()).represents(LOGIN, NAME, LAST_NAME);
    }
}
