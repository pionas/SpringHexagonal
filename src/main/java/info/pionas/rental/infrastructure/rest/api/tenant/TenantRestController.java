package info.pionas.rental.infrastructure.rest.api.tenant;

import info.pionas.rental.application.tenant.TenantApplicationService;
import info.pionas.rental.application.tenant.TenantDto;
import info.pionas.rental.query.PagedResultTransferObject;
import info.pionas.rental.query.PaginationLinkBuilder;
import info.pionas.rental.query.tenant.QueryTenantRepository;
import info.pionas.rental.query.tenant.TenantReadModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tenant")
public class TenantRestController {
    private final TenantApplicationService tenantApplicationService;
    private final QueryTenantRepository queryTenantRepository;

    @PostMapping
    public ResponseEntity<String> add(@Valid @RequestBody TenantDto tenantDto) {
        String tenantId = tenantApplicationService.add(tenantDto);
        return ResponseEntity.created(URI.create("/tenant/" + tenantId)).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable String id, @RequestBody TenantDto tenantDto) {
        tenantApplicationService.update(id, tenantDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        tenantApplicationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public PagedResultTransferObject findAll(@RequestParam(defaultValue = "0") int pageNumber,
                                             @RequestParam(defaultValue = "5") int pageSize) {
        Page<TenantReadModel> tenantReadModels = queryTenantRepository.findAll(pageNumber, pageSize);

        PagedResultTransferObject pagedResultTransferObject = new PagedResultTransferObject(tenantReadModels.getContent(), tenantReadModels.getNumber(), tenantReadModels.getTotalPages());
        pagedResultTransferObject.add(getLinks(pageNumber, pageSize, tenantReadModels.getTotalPages()));
        return pagedResultTransferObject;
    }

    @GetMapping("/{id}")
    public TenantReadModel findById(@PathVariable String id) {
        TenantReadModel tenantReadModel = queryTenantRepository.findById(id);
        tenantReadModel.add(linkTo(methodOn(TenantRestController.class).findById(id)).withSelfRel());
        return tenantReadModel;
    }

    private Iterable<Link> getLinks(int pageNumber, int pageSize, int totalPages) {
        PaginationLinkBuilder paginationLinkBuilder = new PaginationLinkBuilder(pageNumber, totalPages);
        paginationLinkBuilder.setPrevPage(methodOn(TenantRestController.class).findAll(pageNumber - 1, pageSize));
        paginationLinkBuilder.setNextPage(methodOn(TenantRestController.class).findAll(pageNumber + 1, pageSize));
        paginationLinkBuilder.setCurrentPage(methodOn(TenantRestController.class).findAll(pageNumber, pageSize));
        return paginationLinkBuilder.getLinks();
    }
}
