package info.pionas.payment.infrastructure.paymentservice;

import info.pionas.payment.domain.payment.PaymentStatus;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static info.pionas.payment.domain.payment.PaymentStatus.SUCCESS;
import static org.assertj.core.api.Assertions.assertThat;

@Tag("IntegrationTest")
class RestPaymentClientTest {
    private static final String SENDER_ID = UUID.randomUUID().toString();
    private static final String RECIPIENT_ID = UUID.randomUUID().toString();
    private static final BigDecimal AMOUNT = BigDecimal.valueOf(123.45);

    private final RestPaymentClient client = new RestPaymentClient();

    @Test
    void shouldTransfer() {
        PaymentStatus actual = client.transfer(SENDER_ID, RECIPIENT_ID, AMOUNT);

        assertThat(actual).isEqualTo(SUCCESS);
    }
}