package info.pionas.usermanagment.domain.user;

import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {
    void save(User user);

    boolean existWithLogin(String login);
}
