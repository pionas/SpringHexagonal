package info.pionas.rental.infrastructure.persistence.jpa.apartmentoffer;

import info.pionas.rental.domain.apartmentoffer.ApartmentOffer;
import info.pionas.rental.domain.apartmentoffer.ApartmentOfferAssertion;
import info.pionas.rental.domain.apartmentoffer.ApartmentOfferRepository;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static info.pionas.rental.domain.apartmentoffer.ApartmentOffer.Builder.apartmentOffer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Tag("DomainRepositoryIntegrationTest")
public class JpaApartmentOfferRepositoryIntegrationTest {
    private static final String APARTMENT_ID = "1234";
    private static final BigDecimal PRICE = BigDecimal.valueOf(123);
    private static final LocalDate START = LocalDate.of(2020, 10, 11);
    private static final LocalDate END = LocalDate.of(2020, 10, 20);

    @Autowired
    private ApartmentOfferRepository repository;

    @Test
    void shouldThrowExceptionWhenNoApartmentOfferFound() {
        String id = UUID.randomUUID().toString();

        ApartmentOfferDoesNotExistException actual = assertThrows(ApartmentOfferDoesNotExistException.class, () -> repository.findById(id));

        assertThat(actual).hasMessage("Apartment offer with id " + id + " does not exist");
    }

    @Test
    @Transactional
    void shouldReturnExistingApartmentOffer() {
        String existingId = givenExistingApartment(createApartmentOffer());

        ApartmentOffer actual = repository.findById(existingId);

        ApartmentOfferAssertion.assertThat(actual)
                .hasIdEqualTo(existingId)
                .hasApartmentIdEqualTo(APARTMENT_ID)
                .hasPriceEqualTo(PRICE)
                .hasAvailabilityEqualTo(START, END);
    }

    private String givenExistingApartment(ApartmentOffer apartmentOffer) {
        return repository.save(apartmentOffer);
    }

    private ApartmentOffer createApartmentOffer() {
        return apartmentOffer()
                .withApartmentId(APARTMENT_ID)
                .withPrice(PRICE)
                .withAvailability(START, END)
                .build();
    }
}
