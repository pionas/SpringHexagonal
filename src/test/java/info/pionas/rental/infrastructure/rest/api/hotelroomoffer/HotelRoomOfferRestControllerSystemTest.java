package info.pionas.rental.infrastructure.rest.api.hotelroomoffer;

import com.google.common.collect.ImmutableMap;
import info.pionas.rental.application.hotelroomoffer.HotelRoomOffertDto;
import info.pionas.rental.domain.hotel.HotelRoom;
import info.pionas.rental.domain.hotel.HotelRoomFactory;
import info.pionas.rental.infrastructure.json.JsonFactory;
import info.pionas.rental.infrastructure.persistence.jpa.hotelroom.SpringJpaHotelRoomTestRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Tag("SystemTest")
class HotelRoomOfferRestControllerSystemTest {
    private static final String HOTEL_ID = UUID.randomUUID().toString();
    private static final int ROOM_NUMBER = 13;
    private static final Map<String, Double> SPACES_DEFINITION = ImmutableMap.of("RoomOne", 20.0, "RoomTwo", 20.0);
    private static final String DESCRIPTION = "What a lovely place";

    private static final BigDecimal PRICE = BigDecimal.valueOf(123);
    private static final LocalDate START = LocalDate.of(2040, 12, 10);
    private static final LocalDate END = LocalDate.of(2041, 12, 20);

    private final JsonFactory jsonFactory = new JsonFactory();
    private String hotelRoomId;
    @Autowired
    private SpringJpaHotelRoomTestRepository springJpaHotelRoomTestRepository;
    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    void deleteHotel() {
        if (hotelRoomId != null) {
            springJpaHotelRoomTestRepository.deleteById(hotelRoomId);
        }
    }

    @BeforeEach
    void createHotel() {
        if (hotelRoomId == null) {
            hotelRoomId = springJpaHotelRoomTestRepository.save(createHotelRoom());
        }
    }

    @Test
    void shouldCreateHotelRoomOffer() throws Exception {
        mockMvc.perform(post("/hotelroomoffer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonFactory.create(getHotelRoomOfferDto())))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldThrowExceptionWhenHotelRoomNotFound() {
        String nonExistingHotelRoomId = UUID.randomUUID().toString();

        HotelRoomOffertDto dto = new HotelRoomOffertDto(nonExistingHotelRoomId, PRICE, START, END);

        Assertions.assertThrows(Exception.class, () -> {
            mockMvc.perform(post("/hotelroomoffer")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonFactory.create(dto)));
        });
    }

    private HotelRoom createHotelRoom() {
        return new HotelRoomFactory().create(HOTEL_ID, ROOM_NUMBER, SPACES_DEFINITION, DESCRIPTION);
    }

    private HotelRoomOffertDto getHotelRoomOfferDto() {
        return new HotelRoomOffertDto(hotelRoomId, PRICE, START, END);
    }
}