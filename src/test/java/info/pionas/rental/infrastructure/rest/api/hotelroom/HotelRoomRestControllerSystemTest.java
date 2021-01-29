package info.pionas.rental.infrastructure.rest.api.hotelroom;

import com.google.common.collect.ImmutableMap;
import info.pionas.rental.application.hotel.HotelDto;
import info.pionas.rental.application.hotelroom.HotelRoomBookingDto;
import info.pionas.rental.application.hotelroom.HotelRoomDto;
import info.pionas.rental.infrastructure.json.JsonFactory;
import info.pionas.rental.infrastructure.persistence.jpa.booking.SpringJpaBookingTestRepository;
import info.pionas.rental.infrastructure.persistence.jpa.hotel.SpringJpaHotelTestRepository;
import info.pionas.rental.infrastructure.persistence.jpa.hotelbookinghistory.SpringJpaHotelBookingHistoryTestRepository;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    private final List<String> bookingIds = new ArrayList<>();
    private String hotelId;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private SpringJpaHotelTestRepository hotelRepository;
    @Autowired
    private SpringJpaHotelBookingHistoryTestRepository hotelBookingHistoryRepository;
    @Autowired
    private SpringJpaBookingTestRepository bookingRepository;

    @BeforeEach
    void givenHotel() throws Exception {
        HotelDto hotelDto = new HotelDto("Big Hotel", "Florianska", "12-345", "13", "Cracow", "Poland");
        MvcResult result = mockMvc.perform(post("/hotel").contentType(MediaType.APPLICATION_JSON).content(jsonFactory.create(hotelDto)))
                .andExpect(status().isCreated())
                .andReturn();

        hotelId = result.getResponse().getRedirectedUrl().replace("/hotel/", "");
    }

    @AfterEach
    void deleteHotelRooms() {
        hotelRepository.deleteById(hotelId);
        bookingRepository.deleteAll(bookingIds);

        if (!bookingIds.isEmpty()) {
            hotelBookingHistoryRepository.deleteById(hotelId);
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
        String url = save(givenHotelRoom1()).getResponse().getRedirectedUrl();
        String hotelRoomId = url.replace("/hotelroom/", "");
        HotelRoomBookingDto hotelRoomBookingDto = new HotelRoomBookingDto(
                hotelRoomId, "1357", asList(LocalDate.of(2020, 11, 12), LocalDate.of(2020, 12, 1)));

        MvcResult result = mockMvc.perform(put(url.replace("hotelroom/", "hotelroom/book/")).contentType(MediaType.APPLICATION_JSON).content(jsonFactory.create(hotelRoomBookingDto)))
                .andExpect(status().isCreated())
                .andReturn();
        storeBookingId(result);
    }

    private void storeBookingId(MvcResult result) {
        bookingIds.add(result.getResponse().getRedirectedUrl().replace("/booking/", ""));
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
}