package info.pionas.rental.infrastructure.rest.api.hotel;

import info.pionas.rental.infrastructure.json.JsonFactory;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class HotelRestControllerSystemTest {
    private final JsonFactory jsonFactory = new JsonFactory();
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnNothingWhenThereWasNoHotelCreated() throws Exception {
        mockMvc
                .perform(get("/hotel"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*]", Matchers.hasSize(0)))
        ;
    }

    @Test
    void shouldReturnExistingHotels() throws Exception {
        HotelDto hotel1 = new HotelDto("Vidago Palace Hotel", "Parque de Vidago, Apartado", "5425-307", "16", "Vidago", "Portugal");
        HotelDto hotel2 = new HotelDto("Big Hotel", "Florianska", "12-345", "13", "Cracow", "Poland");
        addHotel(hotel1);
        addHotel(hotel2);

        mockMvc
                .perform(get("/hotel"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*]", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.[0].name").value("Vidago Palace Hotel"))
                .andExpect(jsonPath("$.[0].street").value("Parque de Vidago, Apartado"))
                .andExpect(jsonPath("$.[0].buildingNumber").value("16"))
                .andExpect(jsonPath("$.[1].name").value("Big Hotel"))
                .andExpect(jsonPath("$.[1].street").value("Florianska"))
                .andExpect(jsonPath("$.[1].buildingNumber").value("13"))
        ;
    }

    private void addHotel(HotelDto hotelDto) throws Exception {
        mockMvc
                .perform(
                        post("/hotel")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonFactory.create(hotelDto))
                )
                .andExpect(status().isCreated());
    }
}