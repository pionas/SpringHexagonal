package info.pionas.rental.domain.apartmentoffer;

import info.pionas.rental.application.apartmentoffer.ApartmentOffer;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;

import java.math.BigDecimal;
import java.time.LocalDate;

@RequiredArgsConstructor
public
class ApartmetnOfferAssertion {
    private final ApartmentOffer actual;

    public static ApartmetnOfferAssertion assertThat(ApartmentOffer actual) {
        return new ApartmetnOfferAssertion(actual);
    }

    public ApartmetnOfferAssertion hasApartmentIdEqualTo(String expected) {
        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("apartmentId", expected);
        return this;
    }

    public ApartmetnOfferAssertion hasPriceEqualTo(BigDecimal expected) {
        Assertions.assertThat(actual).extracting("money")
                .hasFieldOrPropertyWithValue("value", expected);
        return this;
    }

    public ApartmetnOfferAssertion hasAvailabilityEqualTo(LocalDate start, LocalDate end) {
        Assertions.assertThat(actual).extracting("availability")
                .hasFieldOrPropertyWithValue("start", start)
                .hasFieldOrPropertyWithValue("end", end)
        ;
        return this;
    }
}
