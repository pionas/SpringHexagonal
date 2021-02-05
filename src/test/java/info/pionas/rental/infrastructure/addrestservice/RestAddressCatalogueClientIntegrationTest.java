package info.pionas.rental.infrastructure.addrestservice;

import info.pionas.rental.domain.address.AddressDto;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Tag("IntegrationTest")
class RestAddressCatalogueClientIntegrationTest {
    private static final String STREET = "Grodzka";
    private static final String POSTAL_CODE = "54-321";
    private static final String BUILDING_NUMBER = "13";
    private static final String CITY = "Berlin";
    private static final String COUNTRY = "Germany";

    @Test
    void shouldAlwaysReturnTrue() {
        boolean actual = new RestAddressCatalogueClient().exists(givenAddressDto());
        assertThat(actual).isTrue();
    }

    private AddressDto givenAddressDto() {
        return new AddressDto(STREET, POSTAL_CODE, BUILDING_NUMBER, CITY, COUNTRY);
    }
}