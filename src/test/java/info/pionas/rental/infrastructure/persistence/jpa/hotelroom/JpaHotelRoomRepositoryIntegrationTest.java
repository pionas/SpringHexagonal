package info.pionas.rental.infrastructure.persistence.jpa.hotelroom;

import com.google.common.collect.ImmutableMap;
import info.pionas.rental.domain.hotel.HotelRoom;
import info.pionas.rental.domain.hotel.HotelRoomAssertion;
import info.pionas.rental.domain.hotel.HotelRoomRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.UUID;

import static info.pionas.rental.domain.hotel.HotelRoom.Builder.hotelRoom;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Tag("DomainRepositoryIntegrationTest")
class JpaHotelRoomRepositoryIntegrationTest {
    private static final UUID HOTEL_ID = UUID.randomUUID();
    private static final int ROOM_NUMBER = 42;
    private static final ImmutableMap<String, Double> SPACES_DEFINITION = ImmutableMap.of("Room1", 30.0);
    private static final String DESCRIPTION = "This is very nice place";

    @Autowired
    private HotelRoomRepository hotelRoomRepository;
    @Autowired
    private SpringJpaHotelRoomTestRepository springJpaHotelRoomTestRepository;
    private String hotelRoomId;

    @AfterEach
    void deleteHotelRoom() {
        if (hotelRoomId != null) {
            springJpaHotelRoomTestRepository.deleteById(hotelRoomId);
        }
    }

    @Test
    void shouldThrowExceptionWhenNoHotelRoomFound() {
        String id = UUID.randomUUID().toString();

        HotelRoomDoesNotExistException actual = assertThrows(HotelRoomDoesNotExistException.class, () -> hotelRoomRepository.findById(id));

        assertThat(actual).hasMessage("Hotel Room with id " + id + " does not exist");
    }

    @Test
    @Transactional
    void shouldFindExistingHotelRoom() {
        HotelRoom hotelRoom = hotelRoom()
                .withHotelId(HOTEL_ID)
                .withNumber(ROOM_NUMBER)
                .withSpacesDefinition(SPACES_DEFINITION)
                .withDescription(DESCRIPTION)
                .build();
        hotelRoomId = hotelRoomRepository.save(hotelRoom);

        HotelRoom actual = hotelRoomRepository.findById(hotelRoomId);

        HotelRoomAssertion.assertThat(actual)
                .hasHotelIdEqualTo(HOTEL_ID)
                .hasRoomNumberEqualTo(ROOM_NUMBER)
                .hasSpacesDefinitionEqualTo(SPACES_DEFINITION)
                .hasDescriptionEqualTo(DESCRIPTION);
    }

    @Test
    void shouldRecognizeApartmentDoesNotExist() {
        String id = UUID.randomUUID().toString();

        assertThat(hotelRoomRepository.existById(id)).isFalse();
    }
}