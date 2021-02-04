package info.pionas.rental.domain.hotelroomoffer;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@RequiredArgsConstructor
@Getter(value = AccessLevel.PACKAGE)
public class CreateHotelRoomOffer {
    private final String hotelId;
    private final int number;
    private final BigDecimal price;
    private final LocalDate start;
    private final LocalDate end;
}
