package info.pionas.rental.application.agreement;

import info.pionas.rental.domain.agreement.Agreement;
import info.pionas.rental.domain.agreement.AgreementEventPublisher;
import info.pionas.rental.domain.agreement.AgreementRepository;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class AgreementApplicationService {
    private final AgreementRepository agreementRepository;
    private final AgreementEventPublisher agreementEventPublisher;

    public void accept(UUID agreementId) {
        Agreement agreement = agreementRepository.findById(agreementId);

        agreement.accept(agreementEventPublisher);
    }
}
