package info.pionas.rental.infrastructure.persistence.jpa.apartmentoffer;

import info.pionas.rental.domain.apartmentoffer.ApartmentOfferRepository;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Tag("DomainRepositoryIntegrationTest")
public class JpaApartmentOfferRepositoryIntegrationTest {
    @Autowired
    private ApartmentOfferRepository repository;

    @Test
    void shouldThrowExceptionWhenNoApartmentOfferFound() {
        String id = UUID.randomUUID().toString();

        ApartmentOfferDoesNotExistException actual = assertThrows(ApartmentOfferDoesNotExistException.class, () -> repository.findById(id));

        assertThat(actual).hasMessage("Apartment offer with id " + id + " does not exist");
    }

    private String createApartmentOffer() {
        return null;
    }

    private void givenExistingApartment() {

    }
}
