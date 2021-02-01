package info.pionas.usermanagment.domain.user;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserFactory {

    private final UserRepository userRepository;

    public User create(String login, String name, String lastName) {
        if (userRepository.existWithLogin(login)) {
            throw new UserExistingException(login);
        }
        return User.Builder.user()
                .withLogin(login)
                .withName(name, lastName)
                .build();
    }
}
