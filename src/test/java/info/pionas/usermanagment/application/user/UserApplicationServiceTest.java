package info.pionas.usermanagment.application.user;

import info.pionas.usermanagment.domain.user.User;
import info.pionas.usermanagment.domain.user.UserAssertion;
import info.pionas.usermanagment.domain.user.UserExistingException;
import info.pionas.usermanagment.domain.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;

public class UserApplicationServiceTest {
    private static final String LOGIN = "pionas";
    private static final String NAME = "Adrian";
    private static final String LAST_NAME = "Pionka";
    private final UserRepository userRepository = mock(UserRepository.class);
    private final UserApplicationService service = new UserApplicationService(userRepository);

    @Test
    void shouldRegisterUser() {
        givenNotExistingUserWithLogin(LOGIN);
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        UserDto dto = givenUserDto();

        service.register(dto);

        then(userRepository).should().save(captor.capture());
        UserAssertion.assertThat(captor.getValue()).represents(LOGIN, NAME, LAST_NAME);
    }

    @Test
    void shouldNotRegisterUserWhenGivenLoginALreadyExist() {
        givenExistingUserWithLogin(LOGIN);
        UserExistingException actual = assertThrows(UserExistingException.class, () -> service.register(givenUserDto()));
        assertThat(actual).hasMessage("User with login " + LOGIN + " already exists");
        then(userRepository).should(never()).save(any());
    }

    private void givenNotExistingUserWithLogin(String login) {
        given(userRepository.existWithLogin(login)).willReturn(false);
    }

    private void givenExistingUserWithLogin(String login) {
        given(userRepository.existWithLogin(login)).willReturn(true);
    }

    private UserDto givenUserDto() {
        return new UserDto(LOGIN, NAME, LAST_NAME);
    }

}
