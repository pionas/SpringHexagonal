package info.pionas.rental.domain.address;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

//class AddressTest {
//
//    private static final String STREET_1 = "Unknown";
//    private static final String POSTAL_CODE_1 = "12-345";
//    private static final String BUILDING_NUMBER_1 = "13";
//    private static final String CITY_1 = "Somewhere";
//    private static final String COUNTRY_1 = "Nowhere";
//    private static final String STREET_2 = "Grodzka";
//    private static final String POSTAL_CODE_2 = "54-321";
//    private static final String BUILDING_NUMBER_2 = "13";
//    private static final String CITY_2 = "Berlin";
//    private static final String COUNTRY_2 = "Germany";
//
//    @Test
//    void shouldRecognizeTwoInstancesOfAddressRepresentsTheSameAggregate() {
//        Address address = new Address(STREET_1, POSTAL_CODE_1, BUILDING_NUMBER_1, CITY_1, COUNTRY_1);
//
//        Address actual = new Address(STREET_1, POSTAL_CODE_1, BUILDING_NUMBER_1, CITY_1, COUNTRY_1);
//        Assertions.assertThat(actual.equals(address)).isTrue();
//        Assertions.assertThat(actual.hashCode()).isEqualTo(address.hashCode());
//    }
//
//    @Test
//    void shouldRecognizeAddressDoesNotRepresentTheSameAggregate() {
//        Address address = new Address(STREET_2, POSTAL_CODE_2, BUILDING_NUMBER_2, CITY_2, COUNTRY_2);
//        Address actual = new Address(STREET_1, POSTAL_CODE_1, BUILDING_NUMBER_1, CITY_1, COUNTRY_1);
//
//        Assertions.assertThat(actual.equals(address)).isFalse();
//        Assertions.assertThat(actual.hashCode()).isNotEqualTo(address.hashCode());
//    }
//
//    @Test
//    void shouldRecognizeTheSameInstanceAsTheSameAggregate() {
//        Address actual = new Address(STREET_2, POSTAL_CODE_2, BUILDING_NUMBER_2, CITY_2, COUNTRY_2);
//
//        assertThat(actual.equals(actual)).isTrue();
//        assertThat(actual.hashCode()).isEqualTo(actual.hashCode());
//    }
//
//    @Test
//    void shouldRecognizeNullIsNotTheSameAsAddress() {
//        Address actual = new Address(STREET_2, POSTAL_CODE_2, BUILDING_NUMBER_2, CITY_2, COUNTRY_2);
//
//        Assertions.assertThat(actual.equals(null)).isFalse();
//    }
//}