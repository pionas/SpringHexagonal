package info.pionas.rental.infrastructure.addrestservice;

import info.pionas.rental.domain.address.AddressCatalogue;
import info.pionas.rental.domain.address.AddressDto;
import org.springframework.stereotype.Component;

@Component
public class RestAddressCatalogueClient implements AddressCatalogue {
    @Override
    public boolean exists(AddressDto addressDto) {
        return true;
    }
}
