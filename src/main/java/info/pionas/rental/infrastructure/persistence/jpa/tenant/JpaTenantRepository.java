package info.pionas.rental.infrastructure.persistence.jpa.tenant;

import info.pionas.rental.domain.tenant.Tenant;
import info.pionas.rental.domain.tenant.TenantNotFoundException;
import info.pionas.rental.domain.tenant.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class JpaTenantRepository implements TenantRepository {
    private final SpringJpaTenantRepository springJpaTenantRepository;

    @Override
    public boolean existById(String id) {
        return springJpaTenantRepository.existsById(UUID.fromString(id));
    }

    @Override
    public String save(Tenant tenant) {
        return springJpaTenantRepository.save(tenant).id();
    }

    @Override
    public Tenant findById(String id) {
        return springJpaTenantRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new TenantNotFoundException(id));
    }

    @Override
    public Optional<Tenant> findByEmail(String email) {
        return springJpaTenantRepository.findByEmail(email);
    }

    @Override
    public Optional<Tenant> findByLogin(String login) {
        return springJpaTenantRepository.findByLogin(login);
    }
}
