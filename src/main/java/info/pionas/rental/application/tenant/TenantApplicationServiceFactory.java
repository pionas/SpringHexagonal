package info.pionas.rental.application.tenant;

import info.pionas.rental.domain.tenant.TenantDomainService;
import info.pionas.rental.domain.tenant.TenantRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
class TenantApplicationServiceFactory {
    @Bean
    TenantApplicationService tenantApplicationService(TenantRepository tenantRepository, PasswordEncoder passwordEncoder) {
        TenantDomainService tenantDomainService = new TenantDomainService(tenantRepository, passwordEncoder);
        return new TenantApplicationService(tenantRepository, tenantDomainService);
    }
}
