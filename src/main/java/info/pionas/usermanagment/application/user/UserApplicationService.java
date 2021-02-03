package info.pionas.usermanagment.application.user;

import info.pionas.usermanagment.domain.user.User;
import info.pionas.usermanagment.domain.user.UserFactory;
import info.pionas.usermanagment.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserApplicationService {
    private final UserRepository userRepository;
    private final UserFactory userFactory;

    public String register(UserDto userDto) {
        User user = userFactory.create(userDto.getLogin(), userDto.getName(), userDto.getLastName());
        return userRepository.save(user);
    }

}
