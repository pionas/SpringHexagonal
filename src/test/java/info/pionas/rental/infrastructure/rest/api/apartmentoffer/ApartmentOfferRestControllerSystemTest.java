package info.pionas.rental.infrastructure.rest.api.apartmentoffer;

import com.google.common.collect.ImmutableMap;
import info.pionas.rental.application.apartmentoffer.ApartmentOfferDto;
import info.pionas.rental.domain.apartment.Apartment;
import info.pionas.rental.infrastructure.json.JsonFactory;
import info.pionas.rental.infrastructure.persistence.jpa.apartment.SpringJpaApartmentTestRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static info.pionas.rental.domain.apartment.Apartment.Builder.apartment;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Tag("SystemTest")
class ApartmentOfferRestControllerSystemTest {
    private static final BigDecimal PRICE = BigDecimal.valueOf(123);
    private static final LocalDate START = LocalDate.of(2040, 10, 11);
    private static final LocalDate END = LocalDate.of(2040, 10, 20);

    private final JsonFactory jsonFactory = new JsonFactory();
    private String apartmentId;
    @Autowired
    private SpringJpaApartmentTestRepository springJpaApartmentTestRepository;
    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    void deleteHotel() {
        if (apartmentId != null) {
            springJpaApartmentTestRepository.deleteById(apartmentId);
        }
    }

    @BeforeEach
    void createHotel() {
        if (apartmentId == null) {
            Apartment apartment = apartment()
                    .withOwnerId("1234")
                    .withStreet("Florianska")
                    .withPostalCode("98-765")
                    .withHouseNumber("12")
                    .withApartmentNumber("34")
                    .withCity("Krakow")
                    .withCountry("Poland")
                    .withDescription("The greatest apartment")
                    .withSpacesDefinition(ImmutableMap.of("Room1", 50.0))
                    .build();
            apartmentId = springJpaApartmentTestRepository.save(apartment);
        }
    }

    @Test
    void shouldCreateApartmentOffer() throws Exception {
        mockMvc.perform(post("/apartmentoffer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonFactory.create(getApartmentOfferDto())))
                .andExpect(status().isCreated());
    }

    private ApartmentOfferDto getApartmentOfferDto() {
        return new ApartmentOfferDto(apartmentId, PRICE, START, END);
    }
}