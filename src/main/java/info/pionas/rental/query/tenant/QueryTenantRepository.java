package info.pionas.rental.query.tenant;

import info.pionas.rental.domain.tenant.TenantNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class QueryTenantRepository {
    private final SpringQueryTenantRepository springQueryTenantRepository;

    public Iterable<TenantReadModel> findAll() {
        return springQueryTenantRepository.findAll();
    }

    public TenantReadModel findById(String id) {
        return springQueryTenantRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new TenantNotFoundException(id));
    }
}
