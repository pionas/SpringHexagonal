package info.pionas.rental.domain.offeravailability;

import info.pionas.rental.domain.period.Period;

import javax.persistence.Embeddable;
import java.time.LocalDate;

@Embeddable
@SuppressWarnings("PMD.UnusedPrivateField")
public class OfferAvailability {
    private LocalDate start;
    private LocalDate end;

    private OfferAvailability() {
    }

    private OfferAvailability(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }

    public static OfferAvailability fromStart(LocalDate start) {
        return from(start, start.plusYears(1));
    }

    public static OfferAvailability from(LocalDate start, LocalDate end) {
        if (start.isAfter(end)) {
            throw OfferAvailabilityException.startAfterEnd(start, end);
        }

        if (start.isBefore(LocalDate.now())) {
            throw OfferAvailabilityException.startFromPast(start);
        }

        return new OfferAvailability(start, end);
    }

    public boolean coversAllDaysWithin(Period period) {
        return period.isWithin(start, end);
    }
}
