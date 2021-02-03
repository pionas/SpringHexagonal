package info.pionas.usermanagment.infrastructure.persistence.jpa.user;

import info.pionas.usermanagment.domain.user.User;
import info.pionas.usermanagment.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class JpaUserRepository implements UserRepository {
    private final SpringJpaUserRepository springJpaUserRepository;

    @Override
    public User findById(String id) {
        return springJpaUserRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new UserDoesNotExistException(id));
    }

    @Override
    public String save(User user) {
        return springJpaUserRepository.save(user).id();
    }

    @Override
    public boolean existWithLogin(String login) {
        return springJpaUserRepository.findByLogin(login).isPresent();
    }

}
