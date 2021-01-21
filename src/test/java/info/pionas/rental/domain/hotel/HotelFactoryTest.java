package info.pionas.rental.domain.hotel;

import org.junit.jupiter.api.Test;

/**
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

        HotelAsseration
                .assertThat(actual)
                .hasNameEqualsTo(name)
                .hasAddressEqualsTo(street, postalCode, buildingNumber, city, country);

    }

}
