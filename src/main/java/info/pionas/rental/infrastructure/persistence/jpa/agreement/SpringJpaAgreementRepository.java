package info.pionas.rental.infrastructure.persistence.jpa.agreement;

import info.pionas.rental.domain.agreement.Agreement;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
interface SpringJpaAgreementRepository extends CrudRepository<Agreement, UUID> {
}
