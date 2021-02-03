package info.pionas.rental.domain.tenant;

public interface TenantRepository {
    boolean existById(String tenantId);
}
