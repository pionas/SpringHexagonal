package info.pionas.rental.application.hotelroomoffer;

import info.pionas.rental.domain.hotelroomoffer.CreateHotelRoomOffer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@RequiredArgsConstructor
@Getter
public class HotelRoomOfferDto {
    private final String hotelId;
    private final int number;
    private final String hotelRoomId;
    private final BigDecimal price;
    private final LocalDate start;
    private final LocalDate end;

    public CreateHotelRoomOffer asDto() {
        return new CreateHotelRoomOffer(number, hotelRoomId, price, start, end);
    }
}
