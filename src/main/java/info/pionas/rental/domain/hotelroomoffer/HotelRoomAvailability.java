package info.pionas.rental.domain.hotelroomoffer;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
class HotelRoomAvailability {
    private final LocalDate start;
    private final LocalDate end;

    static HotelRoomAvailability of(LocalDate start, LocalDate end) {
        if (start.isAfter(end)) {
            throw new HotelRoomAvailabilityException(start, end);
        }
        return new HotelRoomAvailability(start, end);
    }
}
