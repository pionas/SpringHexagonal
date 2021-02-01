package info.pionas.usermanagment.application.user;

import info.pionas.usermanagment.domain.user.User;
import info.pionas.usermanagment.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserApplicationService {
    private final UserRepository userRepository;

    public void register(UserDto userDto) {
        User user = User.Builder.user()
                .withLogin(userDto.getLogin())
                .withName(userDto.getName(), userDto.getLastName())
                .build();
        userRepository.save(user);
    }
}
