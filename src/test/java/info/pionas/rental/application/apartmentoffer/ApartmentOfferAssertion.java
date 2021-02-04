package info.pionas.rental.application.apartmentoffer;

import info.pionas.rental.domain.apartmentoffer.ApartmentOffer;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@RequiredArgsConstructor
public
class ApartmentOfferAssertion {
    private final ApartmentOffer actual;

    public static ApartmentOfferAssertion assertThat(ApartmentOffer actual) {
        return new ApartmentOfferAssertion(actual);
    }

    public ApartmentOfferAssertion hasIdEqualTo(UUID expected) {
        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("id", expected);
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
                .hasFieldOrPropertyWithValue("end", end);
        return this;
    }
}
