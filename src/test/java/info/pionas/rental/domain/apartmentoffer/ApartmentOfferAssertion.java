package info.pionas.rental.domain.apartmentoffer;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;

import java.math.BigDecimal;
import java.time.LocalDate;

@RequiredArgsConstructor
public
class ApartmentOfferAssertion {
    private final ApartmentOffer actual;

    public static ApartmentOfferAssertion assertThat(ApartmentOffer actual) {
        return new ApartmentOfferAssertion(actual);
    }

    public ApartmentOfferAssertion hasIdEqualTo(String expected) {
        Assertions.assertThat(actual.getId().toString()).isEqualTo(expected);
        return this;
    }

    public ApartmentOfferAssertion hasApartmentIdEqualTo(String expected) {
        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("apartmentId", expected);
        return this;
    }

    public ApartmentOfferAssertion hasPriceEqualTo(BigDecimal expected) {
        Assertions.assertThat(actual).extracting("money")
                .hasFieldOrPropertyWithValue("value", expected);
        return this;
    }

    public ApartmentOfferAssertion hasAvailabilityEqualTo(LocalDate start, LocalDate end) {
        Assertions.assertThat(actual).extracting("availability")
                .hasFieldOrPropertyWithValue("start", start)
                .hasFieldOrPropertyWithValue("end", end)
        ;
        return this;
    }
}
