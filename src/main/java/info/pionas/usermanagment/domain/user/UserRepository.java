package info.pionas.usermanagment.domain.user;

import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {
    String save(User user);

    boolean existWithLogin(String login);

    User findById(String id);
}
