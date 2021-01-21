package info.pionas.rental.infrastructure.persistence.jpa.hotel;

import info.pionas.rental.domain.hotel.Hotel;
import info.pionas.rental.domain.hotel.HotelAsseration;
import info.pionas.rental.domain.hotel.HotelFactory;
import info.pionas.rental.domain.hotel.HotelRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class JpaHotelRepositoryIntegrationTest {
    private static final String NAME = "Vidago Palace Hotel";
    private static final String STREET = "Parque de Vidago, Apartado";
    private static final String POSTAL_CODE = "5425-307";
    private static final String BUILDING_NUMBER = "16";
    private static final String CITY = "Vidago";
    private static final String COUNTRY = "Portugal";

    private final HotelFactory hotelFactory = new HotelFactory();
    private final List<String> hotelIds = new ArrayList<>();

    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private SpringJpaHotelTestRepository springJpaHotelTestRepository;


    @AfterEach
    void deleteHotels() {
        springJpaHotelTestRepository.deleteAll(hotelIds);
    }

    @Test
    void shoudlThrowExceptionWhenHotelDoesNotExist() {
        String nonExistingAPartmentId = UUID.randomUUID().toString();
        HotelDoesNotExistException actual = assertThrows(HotelDoesNotExistException.class, () -> {
            hotelRepository.findById(nonExistingAPartmentId);
        });
        assertThat(actual).hasMessage("Hotel with id " + nonExistingAPartmentId + " does not exist");
    }

    @Test
    @Transactional
    void shoudlReturnExistingHotel() {
        String existingId = givenExistingHotel(createHotel());

        Hotel actual = hotelRepository.findById(existingId);

        HotelAsseration
                .assertThat(actual)
                .hasNameEqualsTo(NAME)
                .hasAddressEqualsTo(STREET, POSTAL_CODE, BUILDING_NUMBER, CITY, COUNTRY);
    }

    @Test
    @Transactional
    void shoudlReturnExistingHotelWeWant() {
        Hotel hotel1 = hotelFactory.create("Warszawa", "Pl. Powstańców Warszawy", "00-039", "9", "Warsaw", "Poland");
        givenExistingHotel(hotel1);
        String existingId = givenExistingHotel(createHotel());
        Hotel hotel2 = hotelFactory.create("Warszawa2", "Pl. Powstańców Warszawy", "00-039", "9", "Warsaw", "Poland");
        givenExistingHotel(hotel2);
        Hotel hotel3 = hotelFactory.create("Warszawa3", "Pl. Powstańców Warszawy", "00-039", "9", "Warsaw", "Poland");
        givenExistingHotel(hotel3);
        Hotel actual = hotelRepository.findById(existingId);

        HotelAsseration
                .assertThat(actual)
                .hasNameEqualsTo(NAME)
                .hasAddressEqualsTo(STREET, POSTAL_CODE, BUILDING_NUMBER, CITY, COUNTRY);
    }

    private String givenExistingHotel(Hotel hotel) {
        String hotelId = hotelRepository.save(hotel);
        hotelIds.add(hotelId);

        return hotelId;
    }


    private Hotel createHotel() {
        return hotelFactory.create(
                NAME,
                STREET,
                POSTAL_CODE,
                BUILDING_NUMBER,
                CITY,
                COUNTRY
        );
    }
}