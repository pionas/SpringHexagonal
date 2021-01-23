package info.pionas.rental.infrastructure.persistence.jpa.hotel;

import info.pionas.rental.domain.hotel.Hotel;
import info.pionas.rental.domain.hotel.HotelRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static info.pionas.rental.domain.hotel.Hotel.Builder.hotel;
import static info.pionas.rental.domain.hotel.HotelAssertion.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Tag("DomainRepositoryIntegrationTest")
class JpaHotelRepositoryIntegrationTest {
    private static final String NAME = "Great hotel";
    private static final String STREET = "Unknown";
    private static final String POSTAL_CODE = "12-345";
    private static final String BUILDING_NUMBER = "13";
    private static final String CITY = "Somewhere";
    private static final String COUNTRY = "Nowhere";

    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private SpringJpaHotelRepository springJpaHotelRepository;
    @Autowired
    private SpringJpaHotelTestRepository springJpaHotelTestRepository;
    private final List<String> hotelIds = new ArrayList<>();

    @AfterEach
    void deleteApartments() {
        springJpaHotelTestRepository.deleteAll(hotelIds);
    }

    @Test
    void shouldThrowExceptionWhenHotelDoesNotExist() {
        String id = UUID.randomUUID().toString();

        HotelDoesNotExistException actual = assertThrows(HotelDoesNotExistException.class, () -> hotelRepository.findById(id));

        Assertions.assertThat(actual).hasMessage("Hotel with id " + id + " does not exist");
    }

    @Test
    void shouldSaveHotel() {
        String hotelId = givenExistingHotel(createHotel());

        assertThat(findBy(hotelId))
                .hasNameEqualsTo(NAME)
                .hasAddressEqualsTo(STREET, POSTAL_CODE, BUILDING_NUMBER, CITY, COUNTRY);
    }

    @Test
    @Transactional
    void shouldReturnExistingApartmentWeWant() {
        Hotel hotel1 = hotel()
                .withName("Great hotel")
                .withStreet("Florianska")
                .withPostalCode("98-765")
                .withBuildingNumber("12")
                .withCity("Krakow")
                .withCountry("Poland")
                .build();
        givenExistingHotel(hotel1);
        String existingId = givenExistingHotel(createHotel());
        Hotel hotel2 = hotel()
                .withName("Great hotel")
                .withStreet("Florianska")
                .withPostalCode("98-999")
                .withBuildingNumber("10")
                .withCity("Krakow")
                .withCountry("Poland")
                .build();
        givenExistingHotel(hotel2);
        Hotel hotel3 = hotel()
                .withName("Great hotel")
                .withStreet("Florianska")
                .withPostalCode("98-123")
                .withBuildingNumber("11")
                .withCity("Krakow")
                .withCountry("Poland")
                .build();
        givenExistingHotel(hotel3);

        assertThat(findBy(existingId))
                .hasNameEqualsTo(NAME)
                .hasAddressEqualsTo(STREET, POSTAL_CODE, BUILDING_NUMBER, CITY, COUNTRY);
    }

    private String givenExistingHotel(Hotel hotel) {
        String hotelId = hotelRepository.save(hotel);
        hotelIds.add(hotelId);
        return hotelId;
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

    private Hotel findBy(String hotelId) {
        return springJpaHotelRepository.findById(UUID.fromString(hotelId)).get();
    }
}