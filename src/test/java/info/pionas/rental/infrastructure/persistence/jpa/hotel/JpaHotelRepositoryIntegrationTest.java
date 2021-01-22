package info.pionas.rental.infrastructure.persistence.jpa.hotel;

import info.pionas.rental.domain.hotel.Hotel;
import info.pionas.rental.domain.hotel.HotelFactory;
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

import static info.pionas.rental.domain.hotel.HotelAssertion.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Tag("IntegrationTest")
class JpaHotelRepositoryIntegrationTest {
    private static final String NAME = "Great hotel";
    private static final String STREET = "Unknown";
    private static final String POSTAL_CODE = "12-345";
    private static final String BUILDING_NUMBER = "13";
    private static final String CITY = "Somewhere";
    private static final String COUNTRY = "Nowhere";
    private final HotelFactory hotelFactory = new HotelFactory();

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
        Hotel hotel1 = hotelFactory.create("Great hotel", "Florianska", "98-765", "12", "Krakow", "Poland");
        givenExistingHotel(hotel1);
        String existingId = givenExistingHotel(createHotel());
        Hotel hotel2 = hotelFactory.create("Great hotel", "Florianska", "98-999", "10", "Krakow", "Poland");
        givenExistingHotel(hotel2);
        Hotel hotel3 = hotelFactory.create("Great hotel", "Florianska", "98-123", "11", "Krakow", "Poland");
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
        return hotelFactory.create(NAME, STREET, POSTAL_CODE, BUILDING_NUMBER, CITY, COUNTRY);
    }

    private Hotel findBy(String hotelId) {
        return springJpaHotelRepository.findById(UUID.fromString(hotelId)).get();
    }
}