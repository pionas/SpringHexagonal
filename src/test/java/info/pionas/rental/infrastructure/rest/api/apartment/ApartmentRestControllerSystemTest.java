package info.pionas.rental.infrastructure.rest.api.apartment;

import com.google.common.collect.ImmutableMap;
import info.pionas.rental.infrastructure.json.JsonFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Map;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ApartmentRestControllerSystemTest {
    private static final String OWNER_ID = "1234";
    private static final String STREET = "Florianska";
    private static final String POSTAL_CODE = "12-345";
    private static final String HOUSE_NUMBER = "1";
    private static final String APARTMENT_NUMBER = "13";
    private static final String CITY = "Cracow";
    private static final String COUNTRY = "POLAND";
    private static final String DESCRIPTION = "Nice place to stay";
    private static final Map<String, Double> ROOMS_DEFINITION = ImmutableMap.of("Toilet", 10.0, "Bedroom", 30.0);

    private JsonFactory jsonFactory = new JsonFactory();
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnNothingWhenApartmentDoesNotExist() throws Exception {
        String notExistingId = UUID.randomUUID().toString();
        ResultActions actual = mockMvc.perform(get("/apartment/" + notExistingId));

        actual
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.apartment").isEmpty())
                .andExpect(jsonPath("$.bookingHistory").isEmpty())
        ;
    }


    @Test
    void shouldReturnExistingApartment() throws Exception {
        ApartmentDto apartmentDto = new ApartmentDto(OWNER_ID, STREET, POSTAL_CODE, HOUSE_NUMBER, APARTMENT_NUMBER, CITY, COUNTRY, DESCRIPTION, ROOMS_DEFINITION);
        MvcResult mvcResult = mockMvc.perform(
                post("/apartment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonFactory.create(apartmentDto))
        )
                .andExpect(status().isCreated())
                .andReturn();
        ResultActions actual = mockMvc.perform(get(mvcResult.getResponse().getRedirectedUrl()));

        actual
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.apartment.ownerId").value(OWNER_ID))
                .andExpect(jsonPath("$.apartment.street").value(STREET))
                .andExpect(jsonPath("$.apartment.postalCode").value(POSTAL_CODE))
                .andExpect(jsonPath("$.apartment.houseNumber").value(HOUSE_NUMBER))
                .andExpect(jsonPath("$.apartment.apartmentNumber").value(APARTMENT_NUMBER))
                .andExpect(jsonPath("$.apartment.city").value(CITY))
                .andExpect(jsonPath("$.apartment.country").value(COUNTRY))
                .andExpect(jsonPath("$.apartment.description").value(DESCRIPTION))
                .andExpect(jsonPath("$.bookingHistory").isEmpty())
        ;
    }

}