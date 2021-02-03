package info.pionas.rental.domain.offeravailability;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class OfferAvailability {
    private LocalDate start;
    private LocalDate end;

    public static OfferAvailability from(LocalDate start, LocalDate end) {
        if (start.isAfter(end)) {
            throw OfferAvailabilityException.startAfterEnd(start, end);
        }
        if (start.isBefore(LocalDate.now())) {
            throw OfferAvailabilityException.startFromPast(start);
        }
        return new OfferAvailability(start, end);
    }

    public static OfferAvailability fromStart(LocalDate start) {
        return from(start, start.plusYears(1));
    }
}
