package info.pionas.usermanagment.application.user;

import info.pionas.usermanagment.domain.user.User;
import info.pionas.usermanagment.domain.user.UserFactory;
import info.pionas.usermanagment.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserApplicationService {
    private final UserRepository userRepository;

    public void register(UserDto userDto) {
        User user = new UserFactory(userRepository).create(userDto.getLogin(), userDto.getName(), userDto.getLastName());
        userRepository.save(user);
    }
}
