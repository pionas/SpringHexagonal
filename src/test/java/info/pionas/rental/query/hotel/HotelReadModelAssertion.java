package info.pionas.rental.query.hotel;

import org.assertj.core.api.Assertions;

class HotelReadModelAssertion {
    private final HotelReadModel actual;

    private HotelReadModelAssertion(HotelReadModel actual) {
        this.actual = actual;
    }

    static HotelReadModelAssertion assertThat(HotelReadModel actual) {
        return new HotelReadModelAssertion(actual);
    }

    HotelReadModelAssertion hasIdEqualsTo(String expected) {
        Assertions.assertThat(actual.getId().toString()).isEqualTo(expected);
        return this;
    }

    HotelReadModelAssertion hasNameEqualsTo(String expected) {
        Assertions.assertThat(actual.getName()).isEqualTo(expected);
        return this;
    }

    HotelReadModelAssertion hasAddressEqualsTo(String street, String postalCode, String buildingNumber, String city, String country) {
        Assertions.assertThat(actual.getStreet()).isEqualTo(street);
        Assertions.assertThat(actual.getPostalCode()).isEqualTo(postalCode);
        Assertions.assertThat(actual.getBuildingNumber()).isEqualTo(buildingNumber);
        Assertions.assertThat(actual.getCity()).isEqualTo(city);
        Assertions.assertThat(actual.getCountry()).isEqualTo(country);
        return this;
    }
}