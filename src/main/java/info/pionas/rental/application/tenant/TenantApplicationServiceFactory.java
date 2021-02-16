package info.pionas.rental.application.tenant;

import info.pionas.rental.domain.password.PasswordEncoderFactory;
import info.pionas.rental.domain.tenant.TenantDomainService;
import info.pionas.rental.domain.tenant.TenantRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class TenantApplicationServiceFactory {
    @Bean
    TenantApplicationService tenantApplicationService(TenantRepository tenantRepository, PasswordEncoderFactory passwordEncoderFactory) {
        TenantDomainService tenantDomainService = new TenantDomainService(tenantRepository, passwordEncoderFactory);
        return new TenantApplicationService(tenantRepository, tenantDomainService);
    }
}
