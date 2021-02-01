package info.pionas.usermanagment.domain.user;

public interface UserRepository {
    void save(User user);

    boolean existWithLogin(String login);
}
