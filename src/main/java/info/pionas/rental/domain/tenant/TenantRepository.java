package info.pionas.rental.domain.tenant;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TenantRepository {
    boolean existById(String id);

    String save(Tenant tenant);

    Tenant findById(String id);

    Optional<Tenant> findByEmail(String email);

    Optional<Tenant> findByLogin(String login);

    void deleteById(String id);
}
