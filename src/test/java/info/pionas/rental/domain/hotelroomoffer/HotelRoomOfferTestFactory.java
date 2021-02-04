package info.pionas.rental.domain.hotelroomoffer;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

import static info.pionas.rental.domain.hotelroomoffer.HotelRoomOffer.Builder.hotelRoomOffer;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class HotelRoomOfferTestFactory {

    public static HotelRoomOffer create(String hotelId, int hotelRoomNumber, BigDecimal price, LocalDate start, LocalDate end) {
        return hotelRoomOffer()
                .withHotelId(hotelId)
                .withHotelRoomNumber(hotelRoomNumber)
                .withPrice(price)
                .withAvailability(start, end)
                .build();
    }
}