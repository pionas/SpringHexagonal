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
    private final BigDecimal price;
    private final LocalDate start;
    private final LocalDate end;

    CreateHotelRoomOffer asDto() {
        return new CreateHotelRoomOffer(getHotelId(), getNumber(), getPrice(), getStart(), getEnd());
    }
}
