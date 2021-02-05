package info.pionas.payment.infrastructure.paymentservice;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Getter
public class PaymentRequest {
    private final String senderId;
    private final String recipientId;
    private final BigDecimal amount;
}
