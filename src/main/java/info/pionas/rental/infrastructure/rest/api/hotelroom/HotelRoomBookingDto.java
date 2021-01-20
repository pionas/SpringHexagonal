package info.pionas.rental.infrastructure.rest.api.hotelroom;

import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author Adi
 */
@RequiredArgsConstructor
@Getter
class HotelRoomBookingDto {

    private final String tenentId;
    private final List<LocalDate> days;
}
