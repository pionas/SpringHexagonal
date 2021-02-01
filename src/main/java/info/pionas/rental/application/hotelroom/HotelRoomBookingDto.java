package info.pionas.rental.application.hotelroom;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;


@RequiredArgsConstructor
@Getter
public class HotelRoomBookingDto {
    private final String hotelId;
    private final int number;
    private final String tenantId;
    private final List<LocalDate> days;
}
