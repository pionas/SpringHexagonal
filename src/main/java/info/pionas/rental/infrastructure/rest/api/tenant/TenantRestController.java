package info.pionas.rental.infrastructure.rest.api.tenant;

import info.pionas.rental.application.tenant.TenantApplicationService;
import info.pionas.rental.application.tenant.TenantDto;
import info.pionas.rental.query.tenant.QueryTenantRepository;
import info.pionas.rental.query.tenant.TenantReadModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tenant")
public class TenantRestController {
    private final TenantApplicationService tenantApplicationService;
    private final QueryTenantRepository queryTenantRepository;

    @PostMapping
    public ResponseEntity<String> add(@RequestBody TenantDto tenantDto) {
        String tenantId = tenantApplicationService.add(tenantDto);
        return ResponseEntity.created(URI.create("/tenant/" + tenantId)).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable String id, @RequestBody TenantDto tenantDto) {
        tenantApplicationService.update(id, tenantDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public Iterable<TenantReadModel> findAll() {
        return queryTenantRepository.findAll();
    }

    @GetMapping("/{id}")
    public TenantReadModel findById(@PathVariable String id) {
        return queryTenantRepository.findById(id);
    }
}
