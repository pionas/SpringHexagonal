package info.pionas.rental.infrastructure.addrestservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestAddressCatalogueClientFactory {
    @Bean
    RestAddressCatalogueClient restAddressCatalogueClient(@Value("${url.address.service}") String url) {
        return new RestAddressCatalogueClient(new RestTemplate(), url);
    }
}