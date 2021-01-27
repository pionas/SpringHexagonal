package info.pionas.rental.application.hotelroomoffer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@RequiredArgsConstructor
@Getter
public class HotelRoomOffertDto {
    private final String hotelRoomId;
    private final BigDecimal price;
    private final LocalDate start;
    private final LocalDate end;
}
