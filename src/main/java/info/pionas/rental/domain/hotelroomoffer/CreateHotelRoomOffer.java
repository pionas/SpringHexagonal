package info.pionas.rental.domain.hotelroomoffer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@RequiredArgsConstructor
@Getter
public class CreateHotelRoomOffer {
    private final int number;
    private final String hotelRoomId;
    private final BigDecimal price;
    private final LocalDate start;
    private final LocalDate end;
}
