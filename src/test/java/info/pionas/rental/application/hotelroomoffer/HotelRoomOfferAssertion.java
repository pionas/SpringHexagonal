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

    public HotelRoomOfferAssertion hasIdEqualTo(UUID expected) {
        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("id", expected);
        return this;
    }

    public HotelRoomOfferAssertion hasHotelIdEqualTo(String expected) {
        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("hotelId", expected);
        return this;
    }

    public HotelRoomOfferAssertion hasHotelRoomNumberEqualTo(int expected) {
        Assertions.assertThat(actual).hasFieldOrPropertyWithValue("hotelRoomNumber", expected);
        return this;
    }

    public HotelRoomOfferAssertion hasPriceEqualTo(BigDecimal expected) {
        Assertions.assertThat(actual).extracting("money")
                .hasFieldOrPropertyWithValue("value", expected);
        return this;
    }

    public HotelRoomOfferAssertion hasAvailabilityEqualTo(LocalDate start, LocalDate end) {
        Assertions.assertThat(actual).extracting("availability")
                .hasFieldOrPropertyWithValue("start", start)
                .hasFieldOrPropertyWithValue("end", end);
        return this;
    }
}
