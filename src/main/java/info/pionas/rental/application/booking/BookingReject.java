package info.pionas.rental.application.booking;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author Adi
 */
@RequiredArgsConstructor
@Getter(value = AccessLevel.PACKAGE)
public class BookingReject {

    private final String bookingId;
}
