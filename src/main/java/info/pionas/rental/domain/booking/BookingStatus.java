package info.pionas.rental.domain.booking;


import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

public enum BookingStatus {
    OPEN,
    REJECTED,
    ACCEPTED;

    private static final Map<BookingStatus, List<BookingStatus>> ALLOWED_TRANSITIONS = ImmutableMap.of(
            REJECTED, emptyList(),
            ACCEPTED, emptyList(),
            OPEN, asList(REJECTED, ACCEPTED)
    );

    BookingStatus moveTo(BookingStatus bookingStatus) {
        if (ALLOWED_TRANSITIONS.get(this).contains(bookingStatus)) {
            return bookingStatus;
        }
        throw new NotAllowedBookingStatusTransitionException(this, bookingStatus);
    }
}
