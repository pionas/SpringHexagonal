package info.pionas.rental.infrastructure.persistence.jpa.apartmentoffer;

import com.google.common.collect.ImmutableMap;
import info.pionas.rental.application.apartmentoffer.ApartmentOfferAssertion;
import info.pionas.rental.domain.apartment.Apartment;
import info.pionas.rental.domain.apartment.ApartmentRepository;
import info.pionas.rental.domain.apartmentoffer.ApartmentOffer;
import info.pionas.rental.domain.apartmentoffer.ApartmentOfferFactory;
import info.pionas.rental.domain.apartmentoffer.ApartmentOfferRepository;
import info.pionas.rental.infrastructure.persistence.jpa.apartment.SpringJpaApartmentTestRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static info.pionas.rental.domain.apartment.Apartment.Builder.apartment;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Tag("DomainRepositoryIntegrationTest")
class JpaApartmentOfferRepositoryIntegrationTest {
    private static final String OWNER_ID = "1234";
    private static final String STREET = "Florianska";
    private static final String POSTAL_CODE = "12-345";
    private static final String HOUSE_NUMBER = "1";
    private static final String APARTMENT_NUMBER = "13";
    private static final String CITY = "Cracow";
    private static final String COUNTRY = "Poland";
    private static final String DESCRIPTION = "Nice place to stay";
    private static final Map<String, Double> SPACES_DEFINITION = ImmutableMap.of("Toilet", 10.0, "Bedroom", 30.0);
    private static final BigDecimal PRICE = BigDecimal.valueOf(123);
    private static final LocalDate START = LocalDate.of(2040, 10, 11);
    private static final LocalDate END = LocalDate.of(2040, 10, 20);
    @Autowired
    private ApartmentRepository apartmentRepository;
    @Autowired
    private ApartmentOfferRepository apartmentOfferRepository;
    @Autowired
    private SpringJpaApartmentOfferTestRepository springJpaApartmentOfferTestRepository;
    @Autowired
    private SpringJpaApartmentTestRepository springJpaApartmentTestRepository;

    private final List<String> apartmentOfferIds = new ArrayList<>();
    private String apartmentId;

    @BeforeEach
    void createApartment() {
        apartmentId = apartmentRepository.save(givenApartment());
    }

    @AfterEach
    void deleteApartmentOffers() {
        springJpaApartmentOfferTestRepository.deleteAll(apartmentOfferIds);
        springJpaApartmentTestRepository.deleteById(apartmentId);
    }

    @Test
    void shouldThrowExceptionWhenApartmentOfferDoesNotExist() {
        String nonExistingApartmentOfferId = UUID.randomUUID().toString();

        ApartmentOfferDoesNotExistException actual = assertThrows(ApartmentOfferDoesNotExistException.class, () -> {
            apartmentOfferRepository.findById(nonExistingApartmentOfferId);
        });

        assertThat(actual).hasMessage("Apartment offer with id " + nonExistingApartmentOfferId + " does not exist");
    }

    @Test
    void shouldRecognizeWhenApartmentOfferByApartmentIdDoesNotExist() {
        String nonExistingApartmentId = UUID.randomUUID().toString();

        boolean actual = apartmentOfferRepository.existByApartmentId(nonExistingApartmentId);

        assertThat(actual).isFalse();
    }

    @Test
    void shouldRecognizeWhenApartmentOfferByApartmentIdExists() {
        givenExistingApartmentOffer(createApartmentOffer());

        boolean actual = apartmentOfferRepository.existByApartmentId(apartmentId);

        assertThat(actual).isTrue();
    }

    @Test
    @Transactional
    void shouldReturnExistingApartmentOffer() {
        String existingId = givenExistingApartmentOffer(createApartmentOffer());

        ApartmentOffer actual = apartmentOfferRepository.findById(existingId);

        ApartmentOfferAssertion.assertThat(actual)
                .hasIdEqualTo(existingId)
                .hasApartmentIdEqualTo(apartmentId)
                .hasPriceEqualTo(PRICE)
                .hasAvailabilityEqualTo(START, END);
    }

    @Test
    @Transactional
    void shouldReturnExistingApartmentOfferByApartmentId() {
        String existingId = givenExistingApartmentOffer(createApartmentOffer());

        ApartmentOffer actual = apartmentOfferRepository.findByApartmentId(apartmentId);

        ApartmentOfferAssertion.assertThat(actual)
                .hasIdEqualTo(existingId)
                .hasApartmentIdEqualTo(apartmentId)
                .hasPriceEqualTo(PRICE)
                .hasAvailabilityEqualTo(START, END);
    }

    @Test
    @Transactional
    void shouldReturnExistingApartmentOfferWeWant() {
        ApartmentOffer apartmentOffer1 = new ApartmentOfferFactory(apartmentRepository).create(apartmentId, BigDecimal.valueOf(190), START, END);
        givenExistingApartmentOffer(apartmentOffer1);
        String existingId = givenExistingApartmentOffer(createApartmentOffer());
        ApartmentOffer apartment2 = new ApartmentOfferFactory(apartmentRepository).create(apartmentId, BigDecimal.valueOf(190), START, END);
        givenExistingApartmentOffer(apartment2);
        ApartmentOffer apartment3 = new ApartmentOfferFactory(apartmentRepository).create(apartmentId, BigDecimal.valueOf(190), START, END);
        givenExistingApartmentOffer(apartment3);

        ApartmentOffer actual = apartmentOfferRepository.findById(existingId);

        ApartmentOfferAssertion.assertThat(actual)
                .hasIdEqualTo(existingId)
                .hasApartmentIdEqualTo(apartmentId)
                .hasPriceEqualTo(PRICE)
                .hasAvailabilityEqualTo(START, END);
    }

    private String givenExistingApartmentOffer(ApartmentOffer apartmentOffer) {
        String apartmentOfferId = apartmentOfferRepository.save(apartmentOffer);
        apartmentOfferIds.add(apartmentOfferId);

        return apartmentOfferId;
    }

    private ApartmentOffer createApartmentOffer() {
        return new ApartmentOfferFactory(apartmentRepository).create(apartmentId, PRICE, START, END);
    }

    private Apartment givenApartment() {
        return apartment()
                .withOwnerId(OWNER_ID)
                .withStreet(STREET)
                .withPostalCode(POSTAL_CODE)
                .withHouseNumber(HOUSE_NUMBER)
                .withApartmentNumber(APARTMENT_NUMBER)
                .withCity(CITY)
                .withCountry(COUNTRY)
                .withDescription(DESCRIPTION)
                .withSpacesDefinition(SPACES_DEFINITION)
                .build();
    }
}