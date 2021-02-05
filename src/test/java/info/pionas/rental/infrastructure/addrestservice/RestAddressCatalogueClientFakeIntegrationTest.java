package info.pionas.rental.infrastructure.addrestservice;

import info.pionas.rental.domain.address.AddressCatalogue;
import info.pionas.rental.domain.address.AddressDto;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Tests added for coverage because RestAddressCatalogueClientIntegrationTest are commented out due to lack of Address Service
 * on any other environment than local
 */
@Tag("IntegrationTest")
class RestAddressCatalogueClientFakeIntegrationTest {
    private static final String URL = "http://somewhere.com:1234";
    private static final String STREET = "Florianska";
    private static final String POSTAL_CODE = "12-345";
    private static final String BUILDING_NUMBER = "1";
    private static final String CITY = "Cracow";
    private static final String COUNTRY = "Poland";

    @Test
    void shouldCreateAddressCatalogue() {
        AddressCatalogue actual = new RestAddressCatalogueClientFactory().restAddressCatalogueClient(URL);

        assertThat(actual).isInstanceOf(RestAddressCatalogueClient.class);
    }

    @Test
    void shouldRecognizeAddressAsValid() {
        AddressDto addressDto = new AddressDto(STREET, POSTAL_CODE, BUILDING_NUMBER, CITY, COUNTRY);
        AddressCatalogue addressCatalogue = givenAddressCatalogue(addressDto, "VALID");

        boolean actual = addressCatalogue.exists(addressDto);

        assertThat(actual).isTrue();
    }

    @Test
    void shouldRecognizeAddressAsInvalid() {
        AddressDto addressDto = new AddressDto(STREET, POSTAL_CODE, BUILDING_NUMBER, CITY, COUNTRY);
        AddressCatalogue addressCatalogue = givenAddressCatalogue(addressDto, "INVALID");

        boolean actual = addressCatalogue.exists(addressDto);

        assertThat(actual).isFalse();
    }

    private AddressCatalogue givenAddressCatalogue(AddressDto addressDto, String isValid) {
        RestTemplate restTemplate = mock(RestTemplate.class);
        AddressVerification verification = new AddressVerification(isValid);
        given(restTemplate.postForObject(URL + "/address/verify", addressDto, AddressVerification.class)).willReturn(verification);

        return new RestAddressCatalogueClient(restTemplate, URL);
    }
}