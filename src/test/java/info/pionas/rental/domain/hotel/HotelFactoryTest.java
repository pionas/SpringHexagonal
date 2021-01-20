package info.pionas.rental.domain.hotel;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Adi
 */
public class HotelFactoryTest {

    @Test
    public void shouldCreateHotelWillAllRequiredFields() {

        String name = "name";
        String street = "Florianska";
        String postalCode = "12-345";
        String buildingNumber = "1";
        String city = "Cracow";
        String country = "Poland";

        Hotel actual = new HotelFactory().create(
                name,
                street,
                postalCode,
                buildingNumber,
                city,
                country
        );

        assertThat(actual).hasFieldOrPropertyWithValue("name", name);
        assertThat(actual).extracting("address")
                .hasFieldOrPropertyWithValue("street", street)
                .hasFieldOrPropertyWithValue("postalCode", postalCode)
                .hasFieldOrPropertyWithValue("buildingNumber", buildingNumber)
                .hasFieldOrPropertyWithValue("city", city)
                .hasFieldOrPropertyWithValue("country", country);

    }

}
