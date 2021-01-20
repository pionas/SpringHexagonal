package info.pionas.rental.infrastructure.persistence.jpa.apartment;

import com.google.common.collect.ImmutableMap;
import info.pionas.rental.domain.apartment.Apartment;
import info.pionas.rental.domain.apartment.ApartmentAsseration;
import info.pionas.rental.domain.apartment.ApartmentFactory;
import info.pionas.rental.domain.apartment.ApartmentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
class JpaApartmentRepositoryIntegrationTest {
    private static final String OWNER_ID = "1234";
    private static final String STREET = "Florianska";
    private static final String POSTAL_CODE = "12-345";
    private static final String HOUSE_NUMBER = "1";
    private static final String APARTMENT_NUMBER = "13";
    private static final String CITY = "Cracow";
    private static final String COUNTRY = "POLAND";
    private static final String DESCRIPTION = "Nice place to stay";
    private static final Map<String, Double> ROOMS_DEFINITION = ImmutableMap.of("Toilet", 10.0, "Bedroom", 30.0);

    private final ApartmentFactory apartmentFactory = new ApartmentFactory();

    @Autowired
    private ApartmentRepository apartmentRepository;

    @Test
    void shoudlThrowExceptionWhenApartmentDoesNotExist() {
        String nonExistingAPartmentId = UUID.randomUUID().toString();
        ApartmentDoesNotExistException actual = assertThrows(ApartmentDoesNotExistException.class, () -> {
            apartmentRepository.findById(nonExistingAPartmentId);
        });
        assertThat(actual).hasMessage("Apartment with id " + nonExistingAPartmentId + " does not exist");
    }


    @Test
    @Transactional
    void shoudlReturnExistingApartment() {
        Apartment apartment = createApartment();
        String existingId = apartmentRepository.save(apartment);
        Apartment actual = apartmentRepository.findById(existingId);

        ApartmentAsseration
                .assertThat(actual)
                .hasOwnerIdEqualsTo(OWNER_ID)
                .hasDescriptionEqualsTo(DESCRIPTION)
                .hasAddressEqualsTo(STREET, POSTAL_CODE, HOUSE_NUMBER, APARTMENT_NUMBER, CITY, COUNTRY)
                .hasRoomsEqualsTo(ROOMS_DEFINITION);
    }


    private Apartment createApartment() {
        return apartmentFactory.create(
                OWNER_ID,
                STREET,
                POSTAL_CODE,
                HOUSE_NUMBER,
                APARTMENT_NUMBER,
                CITY,
                COUNTRY,
                DESCRIPTION,
                ROOMS_DEFINITION
        );
    }
}