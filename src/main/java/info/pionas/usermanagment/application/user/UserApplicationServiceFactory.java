package info.pionas.usermanagment.application.user;

import info.pionas.usermanagment.domain.user.UserFactory;
import info.pionas.usermanagment.domain.user.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class UserApplicationServiceFactory {
    @Bean
    UserApplicationService userApplicationService(UserRepository userRepository) {
        return new UserApplicationService(userRepository, new UserFactory(userRepository));
    }
}
