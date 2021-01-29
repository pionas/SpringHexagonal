package info.pionas.rental.infrastructure.rest.api.hotelroomoffer;

import com.google.common.collect.ImmutableMap;
import info.pionas.rental.application.hotel.HotelDto;
import info.pionas.rental.application.hotelroom.HotelRoomDto;
import info.pionas.rental.application.hotelroomoffer.HotelRoomOffertDto;
import info.pionas.rental.infrastructure.json.JsonFactory;
import info.pionas.rental.infrastructure.persistence.jpa.hotel.SpringJpaHotelTestRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

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
    private static final int ROOM_NUMBER = 13;
    private static final Map<String, Double> SPACES_DEFINITION = ImmutableMap.of("RoomOne", 20.0, "RoomTwo", 20.0);
    private static final String DESCRIPTION = "What a lovely place";

    private static final BigDecimal PRICE = BigDecimal.valueOf(123);
    private static final LocalDate START = LocalDate.of(2040, 12, 10);
    private static final LocalDate END = LocalDate.of(2041, 12, 20);

    private final JsonFactory jsonFactory = new JsonFactory();
    private String hotelId;
    private String hotelRoomId;
    @Autowired
    private SpringJpaHotelTestRepository springJpaHotelTestRepository;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void givenHotelAndHotelRoom() throws Exception {
        HotelDto hotelDto = new HotelDto("Big Hotel", "Florianska", "12-345", "13", "Cracow", "Poland");
        MvcResult result = mockMvc.perform(post("/hotel").contentType(MediaType.APPLICATION_JSON).content(jsonFactory.create(hotelDto)))
                .andExpect(status().isCreated())
                .andReturn();

        hotelId = result.getResponse().getRedirectedUrl().replace("/hotel/", "");

        HotelRoomDto hotelRoomDto = new HotelRoomDto(hotelId, ROOM_NUMBER, SPACES_DEFINITION, DESCRIPTION);
        result = mockMvc.perform(post("/hotelroom").contentType(MediaType.APPLICATION_JSON).content(jsonFactory.create(hotelRoomDto)))
                .andExpect(status().isCreated())
                .andReturn();
        hotelRoomId = result.getResponse().getRedirectedUrl().replace("/hotelroom/", "");
    }

    @AfterEach
    void deleteHotelRooms() {
        springJpaHotelTestRepository.deleteById(hotelId);
    }

    @Test
    void shouldCreateHotelRoomOfferForExistingHotelRoom() throws Exception {
        HotelRoomOffertDto dto = new HotelRoomOffertDto(hotelId, ROOM_NUMBER, hotelRoomId, PRICE, START, END);
        mockMvc.perform(post("/hotelroomoffer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonFactory.create(dto)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldThrowExceptionWhenHotelRoomNotFound() {
        String nonExistingHotelRoomId = UUID.randomUUID().toString();

        HotelRoomOffertDto dto = new HotelRoomOffertDto(hotelId, ROOM_NUMBER, nonExistingHotelRoomId, PRICE, START, END);

        Assertions.assertThrows(Exception.class, () -> {
            mockMvc.perform(post("/hotelroomoffer")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonFactory.create(dto)));
        });
    }
}