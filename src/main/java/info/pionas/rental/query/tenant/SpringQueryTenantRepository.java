package info.pionas.rental.query.tenant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
interface SpringQueryTenantRepository extends JpaRepository<TenantReadModel, UUID> {

}
