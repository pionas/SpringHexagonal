package info.pionas.rental.infrastructure.persistence.jpa.apartment;

import com.google.common.collect.ImmutableMap;
import info.pionas.rental.domain.apartment.Apartment;
import info.pionas.rental.domain.apartment.ApartmentAssertion;
import info.pionas.rental.domain.apartment.ApartmentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static info.pionas.rental.domain.apartment.Apartment.Builder.apartment;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Tag("DomainRepositoryIntegrationTest")
class JpaApartmentRepositoryIntegrationTest {
    private static final String OWNER_ID = "1234";
    private static final String STREET = "Florianska";
    private static final String POSTAL_CODE = "12-345";
    private static final String HOUSE_NUMBER = "1";
    private static final String APARTMENT_NUMBER = "13";
    private static final String CITY = "Cracow";
    private static final String COUNTRY = "Poland";
    private static final String DESCRIPTION = "Nice place to stay";
    private static final Map<String, Double> ROOMS_DEFINITION = ImmutableMap.of("Toilet", 10.0, "Bedroom", 30.0);

    @Autowired
    private ApartmentRepository apartmentRepository;
    @Autowired
    private SpringJpaApartmentTestRepository springJpaApartmentTestRepository;

    private final List<String> apartmentIds = new ArrayList<>();

    @AfterEach
    void deleteApartments() {
        springJpaApartmentTestRepository.deleteAll(apartmentIds);
    }

    @Test
    void shouldThrowExceptionWhenApartmentDoesNotExist() {
        String nonExistingApartmentId = UUID.randomUUID().toString();

        ApartmentDoesNotExistException actual = assertThrows(ApartmentDoesNotExistException.class, () -> {
            apartmentRepository.findById(nonExistingApartmentId);
        });

        assertThat(actual).hasMessage("Apartment with id " + nonExistingApartmentId + " does not exist");
    }

    @Test
    @Transactional
    void shouldReturnExistingApartment() {
        String existingId = givenExistingApartment(createApartment());

        Apartment actual = apartmentRepository.findById(existingId);

        ApartmentAssertion.assertThat(actual)
                .hasOwnerIdEqualsTo(OWNER_ID)
                .hasDescriptionEqualsTo(DESCRIPTION)
                .hasAddressEqualsTo(STREET, POSTAL_CODE, HOUSE_NUMBER, APARTMENT_NUMBER, CITY, COUNTRY)
                .hasRoomsEqualsTo(ROOMS_DEFINITION);
    }

    @Test
    @Transactional
    void shouldReturnExistingApartmentWeWant() {
        Apartment apartment1 = apartment()
                .withOwnerId("1234")
                .withStreet("Florianska")
                .withPostalCode("98-765")
                .withHouseNumber("12")
                .withApartmentNumber("34")
                .withCity("Krakow")
                .withCountry("Poland")
                .withDescription("The greatest apartment")
                .withRoomsDefinition(ImmutableMap.of("Room1", 50.0))
                .build();
        givenExistingApartment(apartment1);
        String existingId = givenExistingApartment(createApartment());
        Apartment apartment2 = apartment()
                .withOwnerId("5692")
                .withStreet("Florianska")
                .withPostalCode("98-999")
                .withHouseNumber("10")
                .withApartmentNumber("42")
                .withCity("Krakow")
                .withCountry("Poland")
                .withDescription("Great apartment")
                .withRoomsDefinition(ImmutableMap.of("Room42", 100.0))
                .build();
        givenExistingApartment(apartment2);
        Apartment apartment3 = apartment()
                .withOwnerId("2083")
                .withStreet("Florianska")
                .withPostalCode("98-123")
                .withHouseNumber("11")
                .withApartmentNumber("13")
                .withCity("Krakow")
                .withCountry("Poland")
                .withDescription("Not so bad apartment")
                .withRoomsDefinition(ImmutableMap.of("Room13", 30.0))
                .build();
        givenExistingApartment(apartment3);

        Apartment actual = apartmentRepository.findById(existingId);

        ApartmentAssertion.assertThat(actual)
                .hasOwnerIdEqualsTo(OWNER_ID)
                .hasDescriptionEqualsTo(DESCRIPTION)
                .hasAddressEqualsTo(STREET, POSTAL_CODE, HOUSE_NUMBER, APARTMENT_NUMBER, CITY, COUNTRY)
                .hasRoomsEqualsTo(ROOMS_DEFINITION);
    }

    private String givenExistingApartment(Apartment apartment3) {
        String apartmentId = apartmentRepository.save(apartment3);
        apartmentIds.add(apartmentId);

        return apartmentId;
    }

    private Apartment createApartment() {
        return apartment()
                .withOwnerId(OWNER_ID)
                .withStreet(STREET)
                .withPostalCode(POSTAL_CODE)
                .withHouseNumber(HOUSE_NUMBER)
                .withApartmentNumber(APARTMENT_NUMBER)
                .withCity(CITY)
                .withCountry(COUNTRY)
                .withDescription(DESCRIPTION)
                .withRoomsDefinition(ROOMS_DEFINITION)
                .build();
    }
}