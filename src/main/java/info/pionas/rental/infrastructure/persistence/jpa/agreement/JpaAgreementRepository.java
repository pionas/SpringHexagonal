package info.pionas.rental.infrastructure.persistence.jpa.agreement;

import info.pionas.rental.domain.agreement.Agreement;
import info.pionas.rental.domain.agreement.AgreementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaAgreementRepository implements AgreementRepository {
    private final SpringJpaAgreementRepository springJpaAgreementRepository;

    @Override
    public void save(Agreement agreement) {
        springJpaAgreementRepository.save(agreement);
    }
}
