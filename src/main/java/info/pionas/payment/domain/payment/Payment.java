package info.pionas.payment.domain.payment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Getter
public class Payment {
    private final PaymentService paymentService;
    private final PaymenEventPublisher paymenEventPublisher;
    private final String senderId;
    private final String recipientId;
    private final BigDecimal totalAmount;

    @SuppressWarnings("checkstyle:MissingSwitchDefault")
    public void pay() {
        PaymentStatus paymentStatus = paymentService.transfer(getSenderId(), getRecipientId(), getTotalAmount());
        switch (paymentStatus) {
            case SUCCESS:
                paymenEventPublisher.paymentCompleted(getSenderId(), getRecipientId(), getTotalAmount());
                break;
            case NOT_ENOUGH_MONEY:
                paymenEventPublisher.paymentFailed(getSenderId(), getRecipientId(), getTotalAmount());
                break;
        }
    }

}
