package info.pionas.rental.infrastructure.rest.api.hotelroom;

import com.google.common.collect.ImmutableMap;
import info.pionas.rental.infrastructure.json.JsonFactory;
import info.pionas.rental.infrastructure.rest.api.hotel.HotelDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;

import static java.util.Arrays.asList;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Tag("SystemTest")
class HotelRoomRestControllerSystemTest {
    private static final int ROOM_NUMBER_1 = 42;
    private static final ImmutableMap<String, Double> SPACES_DEFINITION_1 = ImmutableMap.of("Room1", 30.0);
    private static final String DESCRIPTION_1 = "This is very nice place";
    private static final int ROOM_NUMBER_2 = 13;
    private static final ImmutableMap<String, Double> SPACES_DEFINITION_2 = ImmutableMap.of("RoomOne", 10.0, "RoomTwo", 25.0);
    private static final String DESCRIPTION_2 = "This is even better place";

    private final JsonFactory jsonFactory = new JsonFactory();
    private String hotelId;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        try {
            HotelDto hotel1 = new HotelDto("Big Hotel", "Florianska", "12-345", "13", "Cracow", "Poland");
            hotelId = addHotel(hotel1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void shouldReturnAllHotelRooms() throws Exception {
        save(givenHotelRoom1());
        save(givenHotelRoom2());

        mockMvc.perform(get("/hotelroom/hotel/" + hotelId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void shouldBookHotelRoom() throws Exception {
        HotelRoomBookingDto hotelRoomBookingDto = new HotelRoomBookingDto("1357", asList(LocalDate.of(2020, 11, 12), LocalDate.of(2020, 12, 1)));
        String url = save(givenHotelRoom1()).getResponse().getRedirectedUrl();

        mockMvc.perform(put(url.replace("hotelroom/", "hotelroom/book/")).contentType(MediaType.APPLICATION_JSON).content(jsonFactory.create(hotelRoomBookingDto)))
                .andExpect(status().isCreated());
    }

    private HotelRoomDto givenHotelRoom1() {
        return new HotelRoomDto(hotelId, ROOM_NUMBER_1, SPACES_DEFINITION_1, DESCRIPTION_1);
    }

    private HotelRoomDto givenHotelRoom2() {
        return new HotelRoomDto(hotelId, ROOM_NUMBER_2, SPACES_DEFINITION_2, DESCRIPTION_2);
    }

    private MvcResult save(HotelRoomDto hotelRoomDto) throws Exception {
        return mockMvc.perform(post("/hotelroom").contentType(MediaType.APPLICATION_JSON).content(jsonFactory.create(hotelRoomDto))).andReturn();
    }

    private String addHotel(HotelDto hotelDto) throws Exception {
        return mockMvc.perform(post("/hotel").contentType(MediaType.APPLICATION_JSON).content(jsonFactory.create(hotelDto)))
                .andExpect(status().isCreated()).andReturn().getResponse().getRedirectedUrl().replace("/hotel/", "");
    }
}