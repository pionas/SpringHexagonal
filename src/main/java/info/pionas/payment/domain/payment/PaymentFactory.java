package info.pionas.payment.domain.payment;

import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class PaymentFactory {
    private final PaymentService paymentService;
    private final PaymentEventPublisher paymentEventPublisher;

    public Payment create(String senderId, String recipientId, List<LocalDate> days, BigDecimal amount) {
        return new Payment(paymentService, paymentEventPublisher, senderId, recipientId, totalAmount(days, amount));
    }

    private BigDecimal totalAmount(List<LocalDate> days, BigDecimal amount) {
        return amount.multiply(BigDecimal.valueOf(days.size()));
    }
}
