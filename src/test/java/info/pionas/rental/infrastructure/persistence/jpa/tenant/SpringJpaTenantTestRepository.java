package info.pionas.rental.infrastructure.persistence.jpa.tenant;

import info.pionas.rental.domain.tenant.Tenant;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Repository
public class SpringJpaTenantTestRepository {

    private final SpringJpaTenantRepository repository;

    public void deleteById(String tenantId) {
        repository.deleteById(UUID.fromString(tenantId));
    }

    public void deleteAll(List<String> tenantIds) {
        tenantIds.forEach(this::deleteById);
    }

    public String save(Tenant tenant) {
        return repository.save(tenant).id();
    }
}

