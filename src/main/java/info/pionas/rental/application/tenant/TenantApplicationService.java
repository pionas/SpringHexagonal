package info.pionas.rental.application.tenant;

import info.pionas.rental.domain.tenant.Tenant;
import info.pionas.rental.domain.tenant.TenantDomainService;
import info.pionas.rental.domain.tenant.TenantRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TenantApplicationService {

    private final TenantRepository tenantRepository;
    private final TenantDomainService tenantDomainService;

    public String add(TenantDto tenantDto) {
        Tenant tenant = tenantDomainService.create(tenantDto.asNewTenantDto());
        return tenantRepository.save(tenant);
    }

    public void update(String tenantId, TenantDto tenantDto) {
        tenantDomainService.update(tenantId, tenantDto.asNewTenantDto());
    }

    public void delete(String tenantId) {
        tenantRepository.deleteById(tenantId);
    }
}
