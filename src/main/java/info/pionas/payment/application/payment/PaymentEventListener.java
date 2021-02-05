package info.pionas.payment.application.payment;

import info.pionas.payment.domain.payment.Payment;
import info.pionas.payment.domain.payment.PaymentFactory;
import info.pionas.rental.domain.agreeement.AgreementAccepted;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PaymentEventListener {
    private final PaymentFactory paymentFactory;

    public void consume(AgreementAccepted givenAgreementAccepted) {
        Payment payment = paymentFactory.create(givenAgreementAccepted.getTenantId(),
                givenAgreementAccepted.getOwnerId(),
                givenAgreementAccepted.getDays(),
                givenAgreementAccepted.getPrice());
        payment.pay();
    }
}
