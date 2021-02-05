package info.pionas.rental.domain.address;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class AddressDtoTest {
    private static final String STREET = "Florianska";
    private static final String POSTAL_CODE = "12-345";
    private static final String HOUSE_NUMBER = "1";
    private static final String CITY = "Cracow";
    private static final String COUNTRY_1 = "Poland";
    private static final String COUNTRY_2 = "USA";

    @Test
    void shouldRecognizeTheSameInstanceAsTheSameAggregate() {
        AddressDto actual = givenAddressDto();

        Assertions.assertThat(actual.equals(actual)).isTrue();
        Assertions.assertThat(actual.hashCode()).isEqualTo(actual.hashCode());
    }

    @Test
    void shouldRecognizeNullIsNotTheSameAsAddressDto() {
        AddressDto actual = givenAddressDto();

        Assertions.assertThat(actual.equals(null)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("notTeSameAddressDtos")
    void shouldRecognizeAddressDtosDoesNotRepresentTheSameAggregate(Object toCompare) {
        AddressDto actual = givenAddressDto();

        Assertions.assertThat(actual.equals(toCompare)).isFalse();
        Assertions.assertThat(actual.hashCode()).isNotEqualTo(toCompare.hashCode());
    }

    private AddressDto givenAddressDto() {
        return new AddressDto(STREET, POSTAL_CODE, HOUSE_NUMBER, CITY, COUNTRY_1);
    }

    private static Stream<Object> notTeSameAddressDtos() {
        return Stream.of(
                new AddressDto(STREET, POSTAL_CODE, HOUSE_NUMBER, CITY, COUNTRY_2),
                new Object()
        );
    }
}