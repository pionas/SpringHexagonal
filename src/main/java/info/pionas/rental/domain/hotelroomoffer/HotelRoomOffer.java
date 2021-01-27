package info.pionas.rental.domain.hotelroomoffer;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@RequiredArgsConstructor
@Getter
public class HotelRoomOffer {
    private final String hotelRoomId;
    private final Money money;
    private final HotelRoomAvailability availability;

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Builder {

        private String hotelRoomId;
        private BigDecimal price;
        private LocalDate start;
        private LocalDate end;

        public static Builder hotelRoomOffer() {
            return new Builder();
        }

        public Builder withHotelRoomId(String hotelRoomId) {
            this.hotelRoomId = hotelRoomId;
            return this;
        }

        public Builder withPrice(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Builder withAvailability(LocalDate start, LocalDate end) {
            this.start = start;
            this.end = end;
            return this;
        }

        public HotelRoomOffer build() {
            return new HotelRoomOffer(hotelRoomId, money(), hotelRoomAvailability());
        }

        private Money money() {
            return Money.of(price);
        }

        private HotelRoomAvailability hotelRoomAvailability() {
            return HotelRoomAvailability.of(start, end);
        }
    }
}
