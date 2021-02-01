package info.pionas.rental.domain.booking;


import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

enum BookingStatus {
    OPEN,
    REJECTED,
    ACCEPTED;

    private static final Map<BookingStatus, List<BookingStatus>> ALLOWED_TRANSATION = ImmutableMap.of(
            REJECTED, emptyList(),
            ACCEPTED, emptyList(),
            OPEN, asList(REJECTED, ACCEPTED)
    );

    BookingStatus moveTo(BookingStatus bookingStatus) {
        if (ALLOWED_TRANSATION.get(this).contains(bookingStatus)) {
            return bookingStatus;
        }
        throw new NotAllowedBookingStatusTransitionException(this, bookingStatus);
    }
}
