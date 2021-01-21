package info.pionas.rental.infrastructure.rest.api.hotel;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class HotelRestControllerSystemTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnNothingWhenThereWasNoHotelCreated() throws Exception {
        mockMvc.perform(get("/hotel"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*]", Matchers.hasSize(0)))
        ;


    }

}