package info.pionas.usermanagment.infrastructure.persistence.jpa.user;

import info.pionas.usermanagment.domain.user.User;
import info.pionas.usermanagment.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaUserRepository implements UserRepository {
    private final SpringJpaUserRepository springJpaUserRepository;

    @Override
    public void save(User user) {
        springJpaUserRepository.save(user);
    }

    @Override
    public boolean existWithLogin(String login) {
        return springJpaUserRepository.findByLogin(login).isPresent();
    }
}
