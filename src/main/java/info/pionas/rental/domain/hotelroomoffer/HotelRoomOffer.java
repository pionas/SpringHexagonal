package info.pionas.rental.domain.hotelroomoffer;

import info.pionas.rental.domain.money.Money;
import info.pionas.rental.domain.offeravailability.OfferAvailability;
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

    private String hotelId;

    private int hotelRoomNumber;
    @Embedded
    private Money money;
    @Embedded
    private OfferAvailability availability;

    public UUID id() {
        return id;
    }

    private HotelRoomOffer(String hotelId, int hotelRoomNumber, Money money, OfferAvailability availability) {
        this.hotelId = hotelId;
        this.hotelRoomNumber = hotelRoomNumber;
        this.money = money;
        this.availability = availability;
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    static class Builder {
        private static final LocalDate NO_END_DATE = null;

        private String hotelId;
        private int hotelRoomNumber;
        private BigDecimal price;
        private LocalDate start;
        private LocalDate end;

        static Builder hotelRoomOffer() {
            return new Builder();
        }

        Builder withHotelId(String hotelId) {
            this.hotelId = hotelId;
            return this;
        }

        Builder withHotelRoomNumber(int hotelRoomNumber) {
            this.hotelRoomNumber = hotelRoomNumber;
            return this;
        }

        Builder withPrice(BigDecimal price) {
            this.price = price;
            return this;
        }

        Builder withAvailability(LocalDate start, LocalDate end) {
            this.start = start;
            this.end = end;
            return this;
        }

        HotelRoomOffer build() {
            return new HotelRoomOffer(hotelId, hotelRoomNumber, money(), hotelRoomAvailability());
        }

        private OfferAvailability hotelRoomAvailability() {
            if (end == NO_END_DATE) {
                return OfferAvailability.fromStart(start);
            }

            return OfferAvailability.from(start, end);
        }

        private Money money() {
            return Money.of(price);
        }
    }
}
