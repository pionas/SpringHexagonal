package info.pionas.rental.application.booking;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter(value = AccessLevel.PACKAGE)
public class BookingAccept {

    private final String bookingId;

}
