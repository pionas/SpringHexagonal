package info.pionas.rental.infrastructure.persistence.jpa.apartmentoffer;

import info.pionas.rental.domain.apartmentoffer.ApartmentOffer;
import info.pionas.rental.application.apartmentoffer.ApartmentOfferAssertion;
import info.pionas.rental.domain.apartmentoffer.ApartmentOfferRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static info.pionas.rental.domain.apartmentoffer.ApartmentOffer.Builder.apartmentOffer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Tag("DomainRepositoryIntegrationTest")
class JpaApartmentOfferRepositoryIntegrationTest {
    private static final String APARTMENT_ID = "1234";
    private static final BigDecimal PRICE = BigDecimal.valueOf(123);
    private static final LocalDate START = LocalDate.of(2040, 10, 11);
    private static final LocalDate END = LocalDate.of(2040, 10, 20);

    @Autowired
    private ApartmentOfferRepository apartmentOfferRepository;
    @Autowired
    private SpringJpaApartmentOfferTestRepository springJpaApartmentOfferTestRepository;

    private final List<String> apartmentOfferIds = new ArrayList<>();

    @AfterEach
    void deleteApartmentOffers() {
        springJpaApartmentOfferTestRepository.deleteAll(apartmentOfferIds);
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
    @Transactional
    void shouldReturnExistingApartmentOffer() {
        String existingId = givenExistingApartmentOffer(createApartmentOffer());

        ApartmentOffer actual = apartmentOfferRepository.findById(existingId);

        ApartmentOfferAssertion.assertThat(actual)
                .hasIdEqualTo(existingId)
                .hasApartmentIdEqualTo(APARTMENT_ID)
                .hasPriceEqualTo(PRICE)
                .hasAvailabilityEqualTo(START, END);
    }

    @Test
    @Transactional
    void shouldReturnExistingApartmentOfferWeWant() {
        ApartmentOffer apartmentOffer1 = apartmentOffer()
                .withApartmentId(APARTMENT_ID)
                .withPrice(BigDecimal.valueOf(190))
                .withAvailability(START, END)
                .build();
        givenExistingApartmentOffer(apartmentOffer1);
        String existingId = givenExistingApartmentOffer(createApartmentOffer());
        ApartmentOffer apartment2 = apartmentOffer()
                .withApartmentId(APARTMENT_ID)
                .withPrice(BigDecimal.valueOf(190))
                .withAvailability(START, END)
                .build();
        givenExistingApartmentOffer(apartment2);
        ApartmentOffer apartment3 = apartmentOffer()
                .withApartmentId(APARTMENT_ID)
                .withPrice(BigDecimal.valueOf(190))
                .withAvailability(START, END)
                .build();
        givenExistingApartmentOffer(apartment3);

        ApartmentOffer actual = apartmentOfferRepository.findById(existingId);

        ApartmentOfferAssertion.assertThat(actual)
                .hasIdEqualTo(existingId)
                .hasApartmentIdEqualTo(APARTMENT_ID)
                .hasPriceEqualTo(PRICE)
                .hasAvailabilityEqualTo(START, END);
    }

    private String givenExistingApartmentOffer(ApartmentOffer apartmentOffer) {
        String apartmentOfferId = apartmentOfferRepository.save(apartmentOffer);
        apartmentOfferIds.add(apartmentOfferId);

        return apartmentOfferId;
    }

    private ApartmentOffer createApartmentOffer() {
        return apartmentOffer()
                .withApartmentId(APARTMENT_ID)
                .withPrice(PRICE)
                .withAvailability(START, END)
                .build();
    }
}