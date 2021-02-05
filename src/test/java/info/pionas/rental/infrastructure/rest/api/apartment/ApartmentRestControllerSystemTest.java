package info.pionas.rental.infrastructure.rest.api.apartment;

import com.google.common.collect.ImmutableMap;
import info.pionas.rental.application.apartment.ApartmentBookingDto;
import info.pionas.rental.application.apartment.ApartmentDto;
import info.pionas.rental.application.apartmentoffer.ApartmentOfferDto;
import info.pionas.rental.infrastructure.json.JsonFactory;
import info.pionas.rental.infrastructure.persistence.jpa.apartment.SpringJpaApartmentTestRepository;
import info.pionas.rental.infrastructure.persistence.jpa.apartmentbookinghistory.SpringJpaApartmentBookingHistoryTestRepository;
import info.pionas.rental.infrastructure.persistence.jpa.booking.SpringJpaBookingTestRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Tag("SystemTest")
@ActiveProfiles("FakeAddressCatalogue")
class ApartmentRestControllerSystemTest {
    private static final String OWNER_ID_1 = "1234";
    private static final String STREET_1 = "Florianska";
    private static final String POSTAL_CODE_1 = "12-345";
    private static final String HOUSE_NUMBER_1 = "1";
    private static final String APARTMENT_NUMBER_1 = "13";
    private static final String CITY_1 = "Cracow";
    private static final String COUNTRY_1 = "Poland";
    private static final String DESCRIPTION_1 = "Nice place to stay";
    private static final Map<String, Double> SPACES_DEFINITION_1 = ImmutableMap.of("Toilet", 10.0, "Bedroom", 30.0);
    private static final String OWNER_ID_2 = "4321";
    private static final String STREET_2 = "Grodzka";
    private static final String POSTAL_CODE_2 = "54-321";
    private static final String HOUSE_NUMBER_2 = "13";
    private static final String APARTMENT_NUMBER_2 = "42";
    private static final String CITY_2 = "Berlin";
    private static final String COUNTRY_2 = "Germany";
    private static final String DESCRIPTION_2 = "Lovely place";
    private static final Map<String, Double> SPACES_DEFINITION_2 = ImmutableMap.of("Toilet", 15.0, "RoomOne", 20.0, "RoomTwo", 25.0);
    private static final BigDecimal PRICE = BigDecimal.valueOf(123);
    private static final LocalDate START = LocalDate.of(2030, 10, 11);
    private static final LocalDate END = LocalDate.of(2050, 10, 20);

    private final JsonFactory jsonFactory = new JsonFactory();
    private final List<String> apartmentIds = new ArrayList<>();
    private final List<String> apartmentBookingHistoryIds = new ArrayList<>();
    private final List<String> bookingIds = new ArrayList<>();
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private SpringJpaApartmentTestRepository apartmentRepository;
    @Autowired
    private SpringJpaApartmentBookingHistoryTestRepository apartmentBookingHistoryRepository;
    @Autowired
    private SpringJpaBookingTestRepository bookingRepository;

    @AfterEach
    void deleteApartments() {
        apartmentRepository.deleteAll(apartmentIds);
        apartmentBookingHistoryRepository.deleteAll(apartmentBookingHistoryIds);
        bookingRepository.deleteAll(bookingIds);
    }

    @Test
    void shouldReturnNothingWhenApartmentDoesNotExist() throws Exception {
        String notExistingId = UUID.randomUUID().toString();

        ResultActions actual = mockMvc.perform(get("/apartment/" + notExistingId));

        actual
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.apartment").isEmpty())
                .andExpect(jsonPath("$.bookingHistory").isEmpty());
    }

    @Test
    void shouldReturnExistingApartment() throws Exception {
        ApartmentDto apartmentDto = givenApartment1();

        MvcResult mvcResult = mockMvc.perform(post("/apartment").contentType(MediaType.APPLICATION_JSON).content(jsonFactory.create(apartmentDto)))
                .andExpect(status().isCreated())
                .andReturn();

        apartmentIds.add(getApartmentId(mvcResult));
        mockMvc.perform(get(mvcResult.getResponse().getRedirectedUrl()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.apartment.ownerId").value(OWNER_ID_1))
                .andExpect(jsonPath("$.apartment.street").value(STREET_1))
                .andExpect(jsonPath("$.apartment.postalCode").value(POSTAL_CODE_1))
                .andExpect(jsonPath("$.apartment.houseNumber").value(HOUSE_NUMBER_1))
                .andExpect(jsonPath("$.bookingHistory").isEmpty());
    }

    @Test
    void shouldBookApartment() throws Exception {
        String url = save(givenApartment1()).getResponse().getRedirectedUrl();
        String apartmentId = url.replace("/apartment/", "");
        givenApartmentOfferFor(apartmentId);
        apartmentBookingHistoryIds.add(apartmentId);
        ApartmentBookingDto apartmentBookingDto = new ApartmentBookingDto(apartmentId, "1357", LocalDate.of(2040, 11, 12), LocalDate.of(2040, 12, 1));

        MvcResult mvcResult = mockMvc.perform(put(url.replace("apartment/", "apartment/book/")).contentType(MediaType.APPLICATION_JSON).content(jsonFactory.create(apartmentBookingDto)))
                .andExpect(status().isCreated())
                .andReturn();

        bookingIds.add(getBookingId(mvcResult));
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookingHistory.bookings.[*]", hasSize(1)))
                .andExpect(jsonPath("$.bookingHistory.bookings.[0].tenantId").value("1357"))
                .andExpect(jsonPath("$.bookingHistory.bookings.[0].periodStart").value("2040-11-12"))
                .andExpect(jsonPath("$.bookingHistory.bookings.[0].periodEnd").value("2040-12-01"));
    }

    private void givenApartmentOfferFor(String apartmentId) throws Exception {
        ApartmentOfferDto dto = new ApartmentOfferDto(apartmentId, PRICE, START, END);

        mockMvc.perform(post("/apartmentoffer").contentType(MediaType.APPLICATION_JSON).content(jsonFactory.create(dto)));
    }

    @Test
    void shouldReturnAllApartments() throws Exception {
        save(givenApartment1());
        save(givenApartment2());

        mockMvc.perform(get("/apartment"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$").isArray());
    }

    private ApartmentDto givenApartment1() {
        return new ApartmentDto(OWNER_ID_1, STREET_1, POSTAL_CODE_1, HOUSE_NUMBER_1, APARTMENT_NUMBER_1, CITY_1, COUNTRY_1, DESCRIPTION_1, SPACES_DEFINITION_1);
    }

    private ApartmentDto givenApartment2() {
        return new ApartmentDto(OWNER_ID_2, STREET_2, POSTAL_CODE_2, HOUSE_NUMBER_2, APARTMENT_NUMBER_2, CITY_2, COUNTRY_2, DESCRIPTION_2, SPACES_DEFINITION_2);
    }

    private MvcResult save(ApartmentDto apartmentDto) throws Exception {
        MvcResult result = mockMvc.perform(post("/apartment").contentType(MediaType.APPLICATION_JSON).content(jsonFactory.create(apartmentDto))).andReturn();
        apartmentIds.add(getApartmentId(result));

        return result;
    }

    private String getApartmentId(MvcResult result) {
        return result.getResponse().getRedirectedUrl().replace("/apartment/", "");
    }

    private String getBookingId(MvcResult result) {
        return result.getResponse().getRedirectedUrl().replace("/booking/", "");
    }
}