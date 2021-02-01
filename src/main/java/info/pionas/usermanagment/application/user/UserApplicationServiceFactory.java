package info.pionas.usermanagment.application.user;

import info.pionas.usermanagment.domain.user.UserFactory;
import info.pionas.usermanagment.domain.user.UserRepository;

public class UserApplicationServiceFactory {
    public UserApplicationService userApplicationService(UserRepository userRepository) {
        return new UserApplicationService(userRepository, new UserFactory(userRepository));
    }
}
