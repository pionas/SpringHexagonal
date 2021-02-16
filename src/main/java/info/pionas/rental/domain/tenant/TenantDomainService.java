package info.pionas.rental.domain.tenant;

import info.pionas.rental.domain.error.ErrorExceptions;
import info.pionas.rental.domain.password.PasswordEncoderFactory;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static info.pionas.rental.domain.tenant.Tenant.Builder.tenant;

@RequiredArgsConstructor
public class TenantDomainService {

    private final TenantRepository tenantRepository;
    private final PasswordEncoderFactory passwordEncoder;

    public Tenant create(NewTenantDto newTenantDto) {
        verifyExistenceOfTenants(null, newTenantDto);
        return tenant()
                .withPasswordEncoder(passwordEncoder)
                .withEmail(newTenantDto.getEmail())
                .withFirstName(newTenantDto.getFirstName())
                .withLastName(newTenantDto.getLastName())
                .withLogin(newTenantDto.getLogin())
                .withPassword(newTenantDto.getPassword(), newTenantDto.getPasswordRepeat())
                .build();
    }

    public void update(String id, NewTenantDto newTenantDto) {
        Tenant tenant = tenantRepository.findById(id);
        verifyExistenceOfTenants(tenant, newTenantDto);
        tenant.update(passwordEncoder, newTenantDto);
        tenantRepository.save(tenant);
    }

    private void verifyExistenceOfTenants(Tenant tenant, NewTenantDto newTenantDto) {
        List<RuntimeException> exceptions = new ArrayList<>();
        verifyEmail(exceptions, tenant, newTenantDto);
        verifyLogin(exceptions, tenant, newTenantDto);
        if (exceptions.size() > 0) {
            throw ErrorExceptions.listExceptions(exceptions);
        }
    }

    private void verifyEmail(List<RuntimeException> exceptions, Tenant tenant, NewTenantDto newTenantDto) {
        Optional<Tenant> tenantByEmail = tenantRepository.findByEmail(newTenantDto.getEmail());
        if (!tenantByEmail.isPresent()) {
            return;
        }
        if (tenant == null) {
            exceptions.add(TenantException.byEmail(newTenantDto.getEmail()));
            return;
        }
        if (!Objects.equals(tenantByEmail.get().getEmail(), tenant.getEmail())) {
            exceptions.add(TenantException.byEmail(newTenantDto.getEmail()));
        }
    }

    private void verifyLogin(List<RuntimeException> exceptions, Tenant tenant, NewTenantDto newTenantDto) {
        Optional<Tenant> tenantByLogin = tenantRepository.findByLogin(newTenantDto.getLogin());
        if (!tenantByLogin.isPresent()) {
            return;
        }
        if (tenant == null) {
            exceptions.add(TenantException.byLogin(newTenantDto.getLogin()));
            return;
        }
        if (!Objects.equals(tenantByLogin.get().getLogin(), tenant.getLogin())) {
            exceptions.add(TenantException.byLogin(newTenantDto.getLogin()));
        }
    }

}
