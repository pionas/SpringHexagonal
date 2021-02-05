package info.pionas.payment.infrastructure.paymentservice;

import info.pionas.payment.domain.payment.PaymentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestPaymentClientFactory {
    @Bean
    public PaymentService paymentService(@Value("${url.payment.service}") String url) {
        return new RestPaymentClient(new RestTemplate(), url);
    }
}
