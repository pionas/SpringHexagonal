package info.pionas.rental.infrastructure.persistence.jpa.tenant;

import info.pionas.rental.domain.tenant.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaTenantRepository implements TenantRepository {
    @Override
    public boolean existById(String tenantId) {
        return true;
    }
}
