package info.pionas.rental.infrastructure.persistence.jpa.agreement;

import info.pionas.rental.domain.agreement.Agreement;
import info.pionas.rental.domain.agreement.AgreementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class JpaAgreementRepository implements AgreementRepository {
    private final SpringJpaAgreementRepository springJpaAgreementRepository;

    @Override
    public UUID save(Agreement agreement) {
        return springJpaAgreementRepository.save(agreement).getId();
    }

    @Override
    public Agreement findById(UUID agreementId) {
        return springJpaAgreementRepository.findById(agreementId).orElseThrow(() -> new AgreementDoesNotExistException(agreementId.toString()));
    }
}
