package info.pionas.usermanagment.infrastructure.persistence.jpa.user;

import info.pionas.usermanagment.domain.user.User;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Repository
public class SpringJpaUserTestRepository {
    private final SpringJpaUserRepository repository;

    public void deleteById(String userId) {
        repository.deleteById(UUID.fromString(userId));
    }

    public void deleteAll(List<String> userIds) {
        userIds.forEach(this::deleteById);
    }

    public String save(User user) {
        return repository.save(user).id();
    }
}