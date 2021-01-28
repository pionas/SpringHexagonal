package info.pionas.rental.infrastructure.rest.api.booking;

import com.google.common.collect.ImmutableMap;
import info.pionas.rental.application.apartment.ApartmentBookingDto;
import info.pionas.rental.infrastructure.json.JsonFactory;
import info.pionas.rental.application.apartment.ApartmentDto;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Tag("SystemTest")
class BookingRestControllerSystemTest {
    private static final String OWNER_ID = "1234";
    private static final String STREET = "Florianska";
    private static final String POSTAL_CODE = "12-345";
    private static final String HOUSE_NUMBER = "1";
    private static final String APARTMENT_NUMBER = "13";
    private static final String CITY = "Cracow";
    private static final String COUNTRY = "Poland";
    private static final String DESCRIPTION = "Nice place to stay";
    private static final Map<String, Double> ROOMS_DEFINITION = ImmutableMap.of("Toilet", 10.0, "Bedroom", 30.0);

    private final JsonFactory jsonFactory = new JsonFactory();

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldRejectBooking() throws Exception {
        String url = getUrlToExistingBooking().replace("booking/", "booking/reject/");

        mockMvc.perform(put(url)).andExpect(status().isOk());
    }

    @Test
    void shouldAcceptBooking() throws Exception {
        String url = getUrlToExistingBooking().replace("booking/", "booking/accept/");

        mockMvc.perform(put(url)).andExpect(status().isOk());
    }

    private String getUrlToExistingBooking() throws Exception {
        ApartmentBookingDto apartmentBookingDto = new ApartmentBookingDto("1357", LocalDate.of(2020, 11, 12), LocalDate.of(2020, 12, 1));
        String url = save(givenApartment()).getResponse().getRedirectedUrl();

        MvcResult mvcResult = mockMvc.perform(put(url.replace("apartment/", "apartment/book/")).contentType(MediaType.APPLICATION_JSON).content(jsonFactory.create(apartmentBookingDto)))
                .andExpect(status().isCreated())
                .andReturn();

        return mvcResult.getResponse().getRedirectedUrl();
    }

    private ApartmentDto givenApartment() {
        return new ApartmentDto(OWNER_ID, STREET, POSTAL_CODE, HOUSE_NUMBER, APARTMENT_NUMBER, CITY, COUNTRY, DESCRIPTION, ROOMS_DEFINITION);
    }

    private MvcResult save(ApartmentDto apartmentDto) throws Exception {
        return mockMvc.perform(post("/apartment").contentType(MediaType.APPLICATION_JSON).content(jsonFactory.create(apartmentDto))).andReturn();
    }
}