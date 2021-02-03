package info.pionas.rental.application.apartmentoffer;

import info.pionas.rental.domain.apartment.ApartmentNotFoundException;
import info.pionas.rental.domain.apartment.ApartmentRepository;
import info.pionas.rental.domain.apartmentoffer.ApartmentAvailabilityException;
import info.pionas.rental.domain.apartmentoffer.ApartmentOffer;
import info.pionas.rental.domain.apartmentoffer.ApartmentOfferRepository;
import info.pionas.rental.domain.money.NotAllowedMoneyValueException;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

class ApartmentOfferApplicationServiceTest {
    private static final String APARTMENT_ID = "1234";
    private static final BigDecimal PRICE = BigDecimal.valueOf(123);
    private static final LocalDate START = LocalDate.of(2020, 10, 11);
    private static final LocalDate END = LocalDate.of(2020, 10, 20);

    private final ApartmentRepository apartmentRepository = mock(ApartmentRepository.class);
    private final ApartmentOfferRepository apartmentOfferRepository = mock(ApartmentOfferRepository.class);
    private final ApartmentOfferApplicationService service = new ApartmentOfferApplicationService(apartmentRepository, apartmentOfferRepository);

    @Test
    void shouldCreateApartmenOfferForExistApartment() {
        ArgumentCaptor<ApartmentOffer> captor = ArgumentCaptor.forClass(ApartmentOffer.class);
        givenExistingApartment();

        service.add(getApartmentOfferDto());

        then(apartmentOfferRepository).should().save(captor.capture());
        ApartmentOffer actual = captor.getValue();
        ApartmentOfferAssertion.assertThat(actual)
                .hasApartmentIdEqualTo(APARTMENT_ID)
                .hasPriceEqualTo(PRICE)
                .hasAvailabilityEqualTo(START, END);
    }

    @Test
    void shouldRecognizeApartmentDoesNotExist() {
        givenNonExistingApartment();

        ApartmentNotFoundException actual = assertThrows(ApartmentNotFoundException.class, () -> {
            service.add(getApartmentOfferDto());
        });
        assertThat(actual).hasMessage("Apartment with id " + APARTMENT_ID + " does not exist");
    }

    @Test
    void shouldRecognizePriseIsNotGreaterThanZero() {
        givenExistingApartment();
        ApartmentOfferDto dto = new ApartmentOfferDto(APARTMENT_ID, BigDecimal.ZERO, START, END);

        NotAllowedMoneyValueException actual = assertThrows(NotAllowedMoneyValueException.class, () -> {
            service.add(dto);
        });
        assertThat(actual).hasMessage("Price 0 is not greater than zero");
    }

    @Test
    void shouldRecognizeThanSartIsAfterEnd() {
        givenExistingApartment();
        ApartmentOfferDto dto = new ApartmentOfferDto(APARTMENT_ID, PRICE, END, START);
        ApartmentAvailabilityException actual = assertThrows(ApartmentAvailabilityException.class, () -> {
            service.add(dto);
        });
        assertThat(actual).hasMessage("Start date: 2020-10-20 of availability is after end date: 2020-10-11");
    }

    private void givenExistingApartment() {
        given(apartmentRepository.existById(APARTMENT_ID)).willReturn(true);
    }

    private void givenNonExistingApartment() {
        given(apartmentRepository.existById(APARTMENT_ID)).willReturn(false);
    }

    private ApartmentOfferDto getApartmentOfferDto() {
        return new ApartmentOfferDto(APARTMENT_ID, PRICE, START, END);
    }
}
