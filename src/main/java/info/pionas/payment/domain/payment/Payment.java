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
        PaymentStatus paymentStatus = getPaymentService().transfer(getSenderId(), getRecipientId(), getTotalAmount());
        switch (paymentStatus) {
            case SUCCESS:
                getPaymentEventPublisher().paymentCompleted(getSenderId(), getRecipientId(), getTotalAmount());
                break;
            case NOT_ENOUGH_MONEY:
                getPaymentEventPublisher().paymentFailed(getSenderId(), getRecipientId(), getTotalAmount());
                break;
        }
    }

}
