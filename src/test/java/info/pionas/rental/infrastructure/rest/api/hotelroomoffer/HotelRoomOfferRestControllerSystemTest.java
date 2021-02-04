package info.pionas.rental.infrastructure.rest.api.hotelroomoffer;

import com.google.common.collect.ImmutableMap;
import info.pionas.rental.application.hotel.HotelDto;
import info.pionas.rental.application.hotel.HotelRoomDto;
import info.pionas.rental.application.hotelroomoffer.HotelRoomOfferDto;
import info.pionas.rental.infrastructure.json.JsonFactory;
import info.pionas.rental.infrastructure.persistence.jpa.hotel.SpringJpaHotelTestRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Tag("SystemTest")
class HotelRoomOfferRestControllerSystemTest {
    private static final BigDecimal PRICE = BigDecimal.valueOf(42);
    private static final LocalDate START = LocalDate.of(2040, 12, 10);
    private static final LocalDate END = LocalDate.of(2041, 12, 20);
    private static final ImmutableMap<String, Double> SPACES_DEFINITION = ImmutableMap.of("Room1", 30.0);
    private static final String DESCRIPTION = "This is very nice place";
    private static final int ROOM_NUMBER = 42;

    private final JsonFactory jsonFactory = new JsonFactory();
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private SpringJpaHotelTestRepository hotelRepository;
    private String hotelId;

    @BeforeEach
    void givenHotel() throws Exception {
        MvcResult result = mockMvc.perform(post("/hotel")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonFactory.create(givenHotelDto())))
                .andExpect(status().isCreated())
                .andReturn();

        hotelId = result.getResponse().getRedirectedUrl().replace("/hotel/", "");

        mockMvc.perform(post("/hotelroom")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonFactory.create(givenHotelRoom())))
                .andExpect(status().isCreated())
                .andReturn();
    }

    @AfterEach
    void deleteHotel() {
        hotelRepository.deleteById(hotelId);
    }

    @Test
    void shouldCreateHotelRoomOffer() throws Exception {
        mockMvc.perform(post("/hotelroomoffer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonFactory.create(givenHotelRoomOfferDto())))
                .andExpect(status().isCreated());
    }

    private HotelDto givenHotelDto() {
        return new HotelDto("Big Hotel", "Florianska", "12-345", "13", "Cracow", "Poland");
    }

    private HotelRoomDto givenHotelRoom() {
        return new HotelRoomDto(hotelId, ROOM_NUMBER, SPACES_DEFINITION, DESCRIPTION);
    }

    private HotelRoomOfferDto givenHotelRoomOfferDto() {
        return new HotelRoomOfferDto(hotelId, ROOM_NUMBER, PRICE, START, END);
    }

}