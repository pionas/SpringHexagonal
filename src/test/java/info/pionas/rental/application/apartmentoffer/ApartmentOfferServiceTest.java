package info.pionas.rental.application.apartmentoffer;

import info.pionas.rental.domain.apartmentoffer.ApartmentOfferRepository;
import info.pionas.rental.domain.apartmentoffer.ApartmetnOfferAssertion;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

class ApartmentOfferServiceTest {
    private static final String APARTMENT_ID = "1234";
    private final ApartmentOfferRepository repository = mock(ApartmentOfferRepository.class);
    private final ApartmentOfferService service = new ApartmentOfferService(repository);

    @Test
    void shouldCreateApartmenOffer() {
        ArgumentCaptor<ApartmentOffer> captor = ArgumentCaptor.forClass(ApartmentOffer.class);
        givenExistingApartment();
        BigDecimal price = BigDecimal.valueOf(123);
        LocalDate start = LocalDate.of(2020, 10, 11);
        LocalDate end = LocalDate.of(2020, 10, 20);

        service.add(APARTMENT_ID, price, start, end);

        then(repository).should().save(captor.capture());
        ApartmentOffer actual = captor.getValue();
        ApartmetnOfferAssertion.assertThat(actual)
                .hasApartmentIdEqualTo(APARTMENT_ID)
                .hasPriceEqualTo(price)
                .hasAvailabilityEqualTo(start, end);
    }

    private void givenExistingApartment() {

    }
}
