package info.pionas.rental.domain.offeravailability;

import java.time.LocalDate;

public class OfferAvailabilityException extends RuntimeException {
    private OfferAvailabilityException(String message) {
        super(message);
    }

    static RuntimeException startAfterEnd(LocalDate start, LocalDate end) {
        return new OfferAvailabilityException("Start date: " + start + " of availability is after end date: " + end + ".");
    }

    static RuntimeException startFromPast(LocalDate start) {
        return new OfferAvailabilityException("Start date: " + start + " is past date.");
    }
}
