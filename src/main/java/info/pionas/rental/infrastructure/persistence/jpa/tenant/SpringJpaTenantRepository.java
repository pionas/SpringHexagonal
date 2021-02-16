package info.pionas.rental.infrastructure.persistence.jpa.tenant;

import info.pionas.rental.domain.tenant.Tenant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
interface SpringJpaTenantRepository extends CrudRepository<Tenant, UUID> {
    Optional<Tenant> findByEmail(String email);

    Optional<Tenant> findByLogin(String login);
}

