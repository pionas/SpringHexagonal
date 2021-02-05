package info.pionas.payment.infrastructure.paymentservice;

import com.google.common.collect.ImmutableMap;
import info.pionas.payment.domain.payment.PaymentStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
@Getter
public class PaymentResponse {
    private static final Map<String, PaymentStatus> STATUSES = ImmutableMap.of(
            "NOT_ENOUGH_RESOURCES", PaymentStatus.NOT_ENOUGH_MONEY,
            "SUCCESS", PaymentStatus.SUCCESS
    );
    private String status;

    public PaymentStatus paymentStatus() {
        return STATUSES.get(status);
    }
}
