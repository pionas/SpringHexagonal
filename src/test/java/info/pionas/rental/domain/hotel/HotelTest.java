package info.pionas.rental.domain.hotel;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static info.pionas.rental.domain.hotel.Hotel.Builder.hotel;
import static org.assertj.core.api.Assertions.assertThat;

class HotelTest {
    private static final String NAME_1 = "Great hotel";
    private static final String STREET_1 = "Florianska";
    private static final String POSTAL_CODE_1 = "12-345";
    private static final String BUILDING_NUMBER_1 = "1";
    private static final String CITY_1 = "Cracow";
    private static final String COUNTRY_1 = "Poland";
    private static final String NAME_2 = "Even greater hotel";
    private static final String STREET_2 = "Grodzka";
    private static final String POSTAL_CODE_2 = "54-321";
    private static final String BUILDING_NUMBER_2 = "13";
    private static final String CITY_2 = "Berlin";
    private static final String COUNTRY_2 = "Germany";


    @Test
    void shouldCreateHotelWithAllRequiredFields() {
        Hotel actual = givenHotel();

        HotelAssertion.assertThat(actual)
                .hasNameEqualsTo(NAME_1)
                .hasAddressEqualsTo(STREET_1, POSTAL_CODE_1, BUILDING_NUMBER_1, CITY_1, COUNTRY_1);
    }

    @Test
    void shouldRecognizTwoHotelInstancesRepresentTheSameAgregate() {
        Hotel toCompare = givenHotel();
        Hotel actual = givenHotel();
        assertThat(actual.equals(toCompare)).isTrue();
        assertThat(actual.hashCode()).isEqualTo(toCompare.hashCode());
    }

    @ParameterizedTest
    @MethodSource("notTheSameHotels")
    void shouldRecognizeHotelDoesNotRepresentTheSameAggregate(Hotel toCompare) {
        Hotel actual = givenHotel();
        assertThat(actual.equals(toCompare)).isFalse();
        assertThat(actual.hashCode()).isNotEqualTo(toCompare.hashCode());
    }

    private static Stream<Hotel> notTheSameHotels() {
        return Stream.of(
                getHotelBuilder().withName(NAME_2).build(),
                getHotelBuilder().withStreet(STREET_2).build(),
                getHotelBuilder().withPostalCode(POSTAL_CODE_2).build(),
                getHotelBuilder().withBuildingNumber(BUILDING_NUMBER_2).build(),
                getHotelBuilder().withCity(CITY_2).build(),
                getHotelBuilder().withCountry(COUNTRY_2).build()
        );
    }

    private Hotel givenHotel() {
        return getHotelBuilder()
                .build();
    }

    private static Hotel.Builder getHotelBuilder() {
        return hotel()
                .withName(NAME_1)
                .withStreet(STREET_1)
                .withPostalCode(POSTAL_CODE_1)
                .withBuildingNumber(BUILDING_NUMBER_1)
                .withCity(CITY_1)
                .withCountry(COUNTRY_1);
    }
}