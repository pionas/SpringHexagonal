package info.pionas.rental.application.hotelroomoffer;

import info.pionas.rental.domain.hotelroomoffer.HotelRoomOffer;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@RequiredArgsConstructor
public class HotelRoomOfferAssertion {
    private final HotelRoomOffer actual;

    public static HotelRoomOfferAssertion assertThat(HotelRoomOffer actual) {
        return new HotelRoomOfferAssertion(actual);
    }


    public HotelRoomOfferAssertion hasIdEqualTo(String expected) {
        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("id", UUID.fromString(expected));
        return this;
    }

    public HotelRoomOfferAssertion hasHotelRoomIdEqualTo(String expected) {
        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("hotelRoomId", expected);
        return this;
    }

    public HotelRoomOfferAssertion hasPriceEqualTo(BigDecimal expected) {
        Assertions.assertThat(actual).extracting("money")
                .hasFieldOrPropertyWithValue("value", expected);
        return this;
    }

    public HotelRoomOfferAssertion hasAvailabilityEqualTo(LocalDate start, LocalDate end) {
        Assertions.assertThat(actual).extracting("availability")
                .hasFieldOrPropertyWithValue("periodStart", start)
                .hasFieldOrPropertyWithValue("periodEnd", end)
        ;
        return this;
    }
}
