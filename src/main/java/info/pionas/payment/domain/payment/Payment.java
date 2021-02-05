package info.pionas.payment.domain.payment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Getter
public class Payment {
    private final PaymentService paymentService;
    private final PaymentEventPublisher paymentEventPublisher;
    private final String senderId;
    private final String recipientId;
    private final BigDecimal totalAmount;

    @SuppressWarnings("checkstyle:MissingSwitchDefault")
    public void pay() {
        PaymentStatus paymentStatus = paymentService.transfer(getSenderId(), getRecipientId(), getTotalAmount());
        switch (paymentStatus) {
            case SUCCESS:
                paymentEventPublisher.paymentCompleted(getSenderId(), getRecipientId(), getTotalAmount());
                break;
            case NOT_ENOUGH_MONEY:
                paymentEventPublisher.paymentFailed(getSenderId(), getRecipientId(), getTotalAmount());
                break;
        }
    }

}
