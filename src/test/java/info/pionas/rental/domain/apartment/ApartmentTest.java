package info.pionas.rental.domain.apartment;

import com.google.common.collect.ImmutableMap;
import info.pionas.rental.domain.booking.Booking;
import info.pionas.rental.domain.booking.BookingAssertion;
import info.pionas.rental.domain.money.Money;
import info.pionas.rental.domain.period.Period;
import info.pionas.rental.domain.space.NotEnoughSpacesGivenException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static info.pionas.rental.domain.apartment.Apartment.Builder.apartment;
import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

class ApartmentTest {
    private static final String OWNER_ID_1 = "1234";
    private static final String STREET_1 = "Florianska";
    private static final String POSTAL_CODE_1 = "12-345";
    private static final String HOUSE_NUMBER_1 = "1";
    private static final String APARTMENT_NUMBER_1 = "13";
    private static final String CITY_1 = "Cracow";
    private static final String COUNTRY_1 = "Poland";
    private static final String DESCRIPTION_1 = "Nice place to stay";
    private static final Map<String, Double> SPACES_DEFINITION_1 = ImmutableMap.of("Toilet", 10.0, "Bedroom", 30.0);
    private static final String OWNER_ID_2 = "4321";
    private static final String STREET_2 = "Grodzka";
    private static final String POSTAL_CODE_2 = "54-321";
    private static final String HOUSE_NUMBER_2 = "13";
    private static final String APARTMENT_NUMBER_2 = "42";
    private static final String CITY_2 = "Berlin";
    private static final String COUNTRY_2 = "Germany";
    private static final String DESCRIPTION_2 = "Lovely place";
    private static final Map<String, Double> SPACES_DEFINITION_2 = ImmutableMap.of("Toilet", 15.0, "RoomOne", 20.0, "RoomTwo", 25.0);
    private static final String TENANT_ID = "137";
    private static final LocalDate START = LocalDate.of(2020, 3, 4);
    private static final LocalDate END = LocalDate.of(2020, 3, 6);
    private static final Period PERIOD = new Period(START, END);
    private static final String NO_ID = null;
    private static final List<Booking> NO_BOOKINGS = emptyList();

    private final ApartmentEventsPublisher apartmentEventsPublisher = Mockito.mock(ApartmentEventsPublisher.class);

    @Test
    void shouldCreateApartmentWithAllRequiredFields() {
        Apartment actual = createApartment1();

        ApartmentAssertion.assertThat(actual)
                .isEqualTo(ApartmentRequirements.apartment()
                        .withOwnerId(OWNER_ID_1)
                        .withApartmentNumber(APARTMENT_NUMBER_1)
                        .withAddress(STREET_1, POSTAL_CODE_1, HOUSE_NUMBER_1, CITY_1, COUNTRY_1)
                )
                .hasDescriptionEqualsTo(DESCRIPTION_1)
                .hasSpacesEqualsTo(SPACES_DEFINITION_1);
    }

    @Test
    void shouldNotBeAbleToCreateApartmentWithoutSpaces() {
        Apartment.Builder apartment = apartment()
                .withOwnerId(OWNER_ID_1)
                .withStreet(STREET_1)
                .withPostalCode(POSTAL_CODE_1)
                .withHouseNumber(HOUSE_NUMBER_1)
                .withApartmentNumber(APARTMENT_NUMBER_1)
                .withCity(CITY_1)
                .withCountry(COUNTRY_1)
                .withDescription(DESCRIPTION_1);

        NotEnoughSpacesGivenException actual = assertThrows(NotEnoughSpacesGivenException.class, apartment::build);

        Assertions.assertThat(actual).hasMessage("No spaces given");
    }

    @Test
    void shouldCreateBookingOnceBooked() {
        Apartment apartment = createApartment1();

        Booking actual = apartment.book(NO_BOOKINGS, TENANT_ID, PERIOD, apartmentEventsPublisher);

        BookingAssertion.assertThat(actual)
                .isEqualToBookingApartment(NO_ID, TENANT_ID, OWNER_ID_1, Money.of(BigDecimal.valueOf(42)), new Period(START, END));
    }

    @Test
    void shouldPublishApartmentBooked() {
        Apartment apartment = createApartment1();

        apartment.book(NO_BOOKINGS, TENANT_ID, PERIOD, apartmentEventsPublisher);

        BDDMockito.then(apartmentEventsPublisher).should().publishApartmentBooked(any(), eq(OWNER_ID_1), eq(TENANT_ID), eq(new Period(START, END)));
    }

    @Test
    void shouldRecognizeTheSameInstanceAsTheSameAggregate() {
        Apartment actual = createApartment1();

        Assertions.assertThat(actual.equals(actual)).isTrue();
        Assertions.assertThat(actual.hashCode()).isEqualTo(actual.hashCode());
    }

    @Test
    void shouldRecognizeTwoInstancesOfApartmentRepresentsTheSameAggregate() {
        Apartment apartment2 = createApartment2SameAsApartment1().build();

        Apartment actual = createApartment1();

        Assertions.assertThat(actual.equals(apartment2)).isTrue();
        Assertions.assertThat(actual.hashCode()).isEqualTo(apartment2.hashCode());
    }

    @Test
    void shouldRecognizeNullIsNotTheSameAsApartment() {
        Apartment actual = createApartment1();

        Assertions.assertThat(actual.equals(null)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("notTheSameApartments")
    void shouldRecognizeApartmentDoesNotRepresentTheSameAggregate(Object notTheSame) {
        Apartment actual = createApartment1();

        Assertions.assertThat(actual.equals(notTheSame)).isFalse();
        Assertions.assertThat(actual.hashCode()).isNotEqualTo(notTheSame.hashCode());
    }

    private static Stream<Object> notTheSameApartments() {
        return Stream.of(
                createApartment2SameAsApartment1().withOwnerId(OWNER_ID_2).build(),
                createApartment2SameAsApartment1().withApartmentNumber(APARTMENT_NUMBER_2).build(),
                createApartment2SameAsApartment1().withStreet(STREET_2).build(),
                createApartment2SameAsApartment1().withPostalCode(POSTAL_CODE_2).build(),
                createApartment2SameAsApartment1().withHouseNumber(HOUSE_NUMBER_2).build(),
                createApartment2SameAsApartment1().withCity(CITY_2).build(),
                createApartment2SameAsApartment1().withCountry(COUNTRY_2).build(),
                new Object()
        );
    }

    private static Apartment.Builder createApartment2SameAsApartment1() {
        return apartment()
                .withOwnerId(OWNER_ID_1)
                .withStreet(STREET_1)
                .withPostalCode(POSTAL_CODE_1)
                .withHouseNumber(HOUSE_NUMBER_1)
                .withApartmentNumber(APARTMENT_NUMBER_1)
                .withCity(CITY_1)
                .withCountry(COUNTRY_1)
                .withDescription(DESCRIPTION_2)
                .withSpacesDefinition(SPACES_DEFINITION_2);
    }

    private Apartment createApartment1() {
        return apartment()
                .withOwnerId(OWNER_ID_1)
                .withStreet(STREET_1)
                .withPostalCode(POSTAL_CODE_1)
                .withHouseNumber(HOUSE_NUMBER_1)
                .withApartmentNumber(APARTMENT_NUMBER_1)
                .withCity(CITY_1)
                .withCountry(COUNTRY_1)
                .withDescription(DESCRIPTION_1)
                .withSpacesDefinition(SPACES_DEFINITION_1)
                .build();
    }
}