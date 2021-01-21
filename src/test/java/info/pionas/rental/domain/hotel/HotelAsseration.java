package info.pionas.rental.domain.hotel;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;

@RequiredArgsConstructor
public class HotelAsseration {

    private final Hotel actual;

    public static HotelAsseration assertThat(Hotel hotel) {
        return new HotelAsseration(hotel);
    }

    public HotelAsseration hasNameEqualsTo(String name) {
        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("name", name);
        return this;
    }


    public HotelAsseration hasAddressEqualsTo(String street, String postalCode, String buildingNumber, String city, String country) {
        Assertions.assertThat(actual).extracting("address")
                .hasFieldOrPropertyWithValue("street", street)
                .hasFieldOrPropertyWithValue("postalCode", postalCode)
                .hasFieldOrPropertyWithValue("buildingNumber", buildingNumber)
                .hasFieldOrPropertyWithValue("city", city)
                .hasFieldOrPropertyWithValue("country", country);
        return this;
    }

}
