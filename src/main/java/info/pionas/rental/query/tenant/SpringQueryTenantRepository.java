package info.pionas.rental.query.tenant;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
interface SpringQueryTenantRepository extends CrudRepository<TenantReadModel, UUID> {

}