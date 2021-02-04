package info.pionas.rental.domain.tenant;

import org.springframework.stereotype.Repository;

@Repository
public interface TenantRepository {
    boolean existById(String tenantId);
}
