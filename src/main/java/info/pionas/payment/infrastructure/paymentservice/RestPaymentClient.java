package info.pionas.payment.infrastructure.paymentservice;

import info.pionas.payment.domain.payment.PaymentService;
import info.pionas.payment.domain.payment.PaymentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class RestPaymentClient implements PaymentService {
    private final RestTemplate restTemplate;
    private final String url;

    @Override
    public PaymentStatus transfer(String senderId, String recipientId, BigDecimal amount) {
        PaymentRequest request = new PaymentRequest(senderId, recipientId, amount);
        PaymentResponse response = restTemplate.postForObject(url.concat("/payment"), request, PaymentResponse.class);
        return response.paymentStatus();
    }

}
