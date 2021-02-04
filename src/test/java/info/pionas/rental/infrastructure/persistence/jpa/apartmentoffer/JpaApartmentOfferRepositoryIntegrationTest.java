package info.pionas.rental.infrastructure.persistence.jpa.apartmentoffer;

import info.pionas.rental.application.apartmentoffer.ApartmentOfferAssertion;
import info.pionas.rental.domain.apartmentoffer.ApartmentOffer;
import info.pionas.rental.domain.apartmentoffer.ApartmentOfferRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static info.pionas.rental.domain.apartmentoffer.ApartmentOffer.Builder.apartmentOffer;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Tag("DomainRepositoryIntegrationTest")
class JpaApartmentOfferRepositoryIntegrationTest {
    private static final String APARTMENT_ID = "1234";
    private static final BigDecimal PRICE = BigDecimal.valueOf(123.45);
    private static final LocalDate START = LocalDate.of(2040, 10, 11);
    private static final LocalDate END = LocalDate.of(2040, 10, 20);

    @Autowired
    private ApartmentOfferRepository apartmentOfferRepository;
    @Autowired
    private SpringJpaApartmentOfferRepository springJpaApartmentOfferRepository;

    private UUID apartmentOfferId;

    @AfterEach
    void deleteApartmentOffer() {
        if (apartmentOfferId != null) {
            springJpaApartmentOfferRepository.deleteById(apartmentOfferId);
        }
    }

    @Test
    void shouldSaveApartmentOffer() {
        ApartmentOffer apartmentOffer = givenApartmentOffer();

        apartmentOfferId = apartmentOfferRepository.save(apartmentOffer);

        assertApartmentOffer(springJpaApartmentOfferRepository.findById(apartmentOfferId).get());
    }

    @Test
    void shouldFindApartmentOfferByApartmentId() {
        givenExistingApartmentOffer();

        ApartmentOffer actual = apartmentOfferRepository.findByApartmentId(APARTMENT_ID);

        assertApartmentOffer(actual);
    }

    @Test
    void shouldRecognizeThereIsNoOfferForGivenApartmentId() {
        boolean actual = apartmentOfferRepository.existByApartmentId(APARTMENT_ID);

        assertThat(actual).isFalse();
    }

    @Test
    void shouldRecognizeThereIsOfferForGivenApartmentId() {
        givenExistingApartmentOffer();

        boolean actual = apartmentOfferRepository.existByApartmentId(APARTMENT_ID);

        assertThat(actual).isTrue();
    }

    private void givenExistingApartmentOffer() {
        ApartmentOffer apartmentOffer = givenApartmentOffer();
        apartmentOfferId = apartmentOfferRepository.save(apartmentOffer);
    }

    private void assertApartmentOffer(ApartmentOffer actual) {
        ApartmentOfferAssertion.assertThat(actual)
                .hasIdEqualTo(apartmentOfferId)
                .hasApartmentIdEqualTo(APARTMENT_ID)
                .hasPriceEqualTo(PRICE)
                .hasAvailabilityEqualTo(START, END);
    }

    private ApartmentOffer givenApartmentOffer() {
        ApartmentOffer apartmentOffer = apartmentOffer()
                .withApartmentId(APARTMENT_ID)
                .withPrice(PRICE)
                .withAvailability(START, END)
                .build();
        return apartmentOffer;
    }
}