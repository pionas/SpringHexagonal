package info.pionas.rental.infrastructure.addrestservice;

import info.pionas.rental.domain.address.AddressCatalogue;
import info.pionas.rental.domain.address.AddressDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class RestAddressCatalogueClient implements AddressCatalogue {
    private final RestTemplate restTemplate;
    private final String url;

    @Override
    public boolean exists(AddressDto addressDto) {
        AddressVerification verification = restTemplate.postForObject(url.concat("/address/verify"), addressDto, AddressVerification.class);
        return verification.isValid();
    }
}
