package info.pionas.rental.application.agreement;

import info.pionas.rental.domain.agreement.AgreementEventPublisher;
import info.pionas.rental.domain.agreement.AgreementRepository;
import info.pionas.rental.domain.clock.Clock;
import info.pionas.rental.domain.event.EventIdFactory;
import info.pionas.rental.domain.eventchannel.EventChannel;

public class AgreementApplicationServiceFactory {
    public AgreementApplicationService agreementApplicationService(AgreementRepository agreementRepository, EventChannel eventChannel, EventIdFactory eventIdFactory, Clock clock) {
        return new AgreementApplicationService(agreementRepository, new AgreementEventPublisher(eventChannel, eventIdFactory, clock));
    }
}
