package info.pionas.rental.infrastructure.rest.api.apartment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import info.pionas.rental.domain.apartment.Period;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

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
    private static final String TENANT_ID = "137";
    private static final LocalDate START = LocalDate.of(2020, 3, 4);
    private static final LocalDate MIDDLE = LocalDate.of(2020, 3, 5);
    private static final LocalDate END = LocalDate.of(2020, 3, 6);
    private static final Period PERIOD = new Period(START, END);

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnNothingWhenApartmentDoesNotExist() throws Exception {
        String notExistingId = UUID.randomUUID().toString();
        ResultActions actual = mockMvc.perform(MockMvcRequestBuilders.get("/apartment/" + notExistingId));

        actual
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.apartment").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.bookingHistory").isEmpty())
        ;
    }


    @Test
    void shouldReturnExistingApartment() throws Exception {
        ApartmentDto apartmentDto = new ApartmentDto(OWNER_ID, STREET, POSTAL_CODE, HOUSE_NUMBER, APARTMENT_NUMBER, CITY, COUNTRY, DESCRIPTION, ROOMS_DEFINITION);
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/apartment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(apartmentDto))
        )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();
        ResultActions actual = mockMvc.perform(MockMvcRequestBuilders.get(mvcResult.getResponse().getRedirectedUrl()));

        actual
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.apartment.ownerId").value(OWNER_ID))
                .andExpect(MockMvcResultMatchers.jsonPath("$.apartment.street").value(STREET))
                .andExpect(MockMvcResultMatchers.jsonPath("$.apartment.postalCode").value(POSTAL_CODE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.apartment.houseNumber").value(HOUSE_NUMBER))
                .andExpect(MockMvcResultMatchers.jsonPath("$.apartment.apartmentNumber").value(APARTMENT_NUMBER))
                .andExpect(MockMvcResultMatchers.jsonPath("$.apartment.city").value(CITY))
                .andExpect(MockMvcResultMatchers.jsonPath("$.apartment.country").value(COUNTRY))
                .andExpect(MockMvcResultMatchers.jsonPath("$.apartment.description").value(DESCRIPTION))
                .andExpect(MockMvcResultMatchers.jsonPath("$.bookingHistory").isEmpty())
        ;
    }

    private String asJson(ApartmentDto apartmentDto) {
        try {
            return new ObjectMapper().writeValueAsString(apartmentDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}