package info.pionas.rental.domain.hotel;

import org.junit.jupiter.api.Test;

import static info.pionas.rental.domain.hotel.Hotel.Builder.hotel;

class HotelTest {
    private static final String NAME = "Great hotel";
    private static final String STREET = "Unknown";
    private static final String POSTAL_CODE = "12-345";
    private static final String BUILDING_NUMBER = "13";
    private static final String CITY = "Somewhere";
    private static final String COUNTRY = "Nowhere";

    @Test
    void shouldCreateHotelWithAllRequiredFields() {
        Hotel actual = createHotel();

        HotelAssertion.assertThat(actual)
                .hasNameEqualsTo(NAME)
                .hasAddressEqualsTo(STREET, POSTAL_CODE, BUILDING_NUMBER, CITY, COUNTRY);
    }

    private Hotel createHotel() {
        return hotel()
                .withName(NAME)
                .withStreet(STREET)
                .withPostalCode(POSTAL_CODE)
                .withBuildingNumber(BUILDING_NUMBER)
                .withCity(CITY)
                .withCountry(COUNTRY)
                .build();
    }
}