package info.pionas.rental.infrastructure.persistence.jpa.agreement;

import info.pionas.rental.domain.agreement.Agreement;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Repository
public class SpringJpaAgreementTestRepository {
    private final SpringJpaAgreementRepository repository;

    public void deleteById(UUID agreementId) {
        repository.deleteById(agreementId);
    }

    public UUID save(Agreement agreement) {
        return repository.save(agreement).getId();
    }
}