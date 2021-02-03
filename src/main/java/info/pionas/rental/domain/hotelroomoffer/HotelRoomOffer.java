package info.pionas.rental.domain.hotelroomoffer;

import info.pionas.rental.domain.money.Money;
import info.pionas.rental.domain.period.Period;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "HOTEL_ROOM_OFFER")
public class HotelRoomOffer {
    @Id
    @GeneratedValue
    private UUID id;
    private String hotelRoomId;
    @Embedded
    private Money money;
    @Embedded
    private Period availability;

    public String id() {
        return id.toString();
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    static class Builder {
        private static final LocalDate NO_END_DATE = null;
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
            return new HotelRoomOffer(null, hotelRoomId, money(), hotelRoomAvailability());
        }

        private Money money() {
            return Money.of(price);
        }

        private Period hotelRoomAvailability() {
            if (end == NO_END_DATE) {
                return Period.fromStart(start);
            }
            return Period.from(start, end);
        }
    }
}
