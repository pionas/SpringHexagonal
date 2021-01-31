package info.pionas.rental.query.hotelroom;

import com.google.common.collect.ImmutableMap;
import info.pionas.rental.domain.hotel.Hotel;
import info.pionas.rental.domain.hotel.HotelRepository;
import info.pionas.rental.infrastructure.persistence.jpa.hotel.SpringJpaHotelTestRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static info.pionas.rental.domain.hotel.Hotel.Builder.hotel;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Tag("IntegrationTest")
class QueryHotelRoomRepositoryIntegrationTest {
    private static final int ROOM_NUMBER_1 = 42;
    private static final ImmutableMap<String, Double> SPACES_DEFINITION_1 = ImmutableMap.of("Room1", 30.0);
    private static final String DESCRIPTION_1 = "This is very nice place";
    private static final int ROOM_NUMBER_2 = 13;
    private static final ImmutableMap<String, Double> SPACES_DEFINITION_2 = ImmutableMap.of("RoomOne", 10.0, "RoomTwo", 25.0);
    private static final String DESCRIPTION_2 = "This is even better place";

    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private QueryHotelRoomRepository queryHotelRoomRepository;
    @Autowired
    private SpringJpaHotelTestRepository springJpaHotelRepository;
    private String hotelId;

    @AfterEach
    void deleteHotelRooms() {
        springJpaHotelRepository.deleteById(hotelId);
    }

    @Test
    @Transactional
    void shouldReturnAllHotelRooms() {
        Hotel hotel = existingHotel();
        hotel.addRoom(ROOM_NUMBER_1, SPACES_DEFINITION_1, DESCRIPTION_1);
        hotel.addRoom(ROOM_NUMBER_2, SPACES_DEFINITION_2, DESCRIPTION_2);
        String hotelId = hotelRepository.save(hotel);

        Iterable<HotelRoomReadModel> actual = queryHotelRoomRepository.findAll(hotelId);

        assertThat(actual)
                .hasSize(2)
                .anySatisfy(hotelRoomReadModel -> {
                    HotelRoomReadModelAssertion.assertThat(hotelRoomReadModel)
                            .hasHotelRoomIdThatIsUUID()
                            .hasHotelIdEqualTo(hotelId)
                            .hasNumberEqualTo(ROOM_NUMBER_1)
                            .hasSpacesDefinitionEqualTo(SPACES_DEFINITION_1)
                            .hasDescriptionEqualTo(DESCRIPTION_1);
                })
                .anySatisfy(hotelRoomReadModel -> {
                    HotelRoomReadModelAssertion.assertThat(hotelRoomReadModel)
                            .hasHotelRoomIdThatIsUUID()
                            .hasHotelIdEqualTo(hotelId)
                            .hasNumberEqualTo(ROOM_NUMBER_2)
                            .hasSpacesDefinitionEqualTo(SPACES_DEFINITION_2)
                            .hasDescriptionEqualTo(DESCRIPTION_2);
                });
    }

    private Hotel existingHotel() {
        Hotel hotel = hotel().build();
        hotelId = hotelRepository.save(hotel);
        return hotel;
    }
}