package info.pionas.rental.infrastructure.rest.api.hotelroom;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Adi
 */
@RequiredArgsConstructor
@Getter
class HotelRoomBookingDto {

    private final String tenentId;
    private final List<LocalDate> days;
}
