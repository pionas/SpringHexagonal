package info.pionas.rental.domain.apartmentoffer;

import java.time.LocalDate;

public class ApartmentAvailabilityException extends RuntimeException {
    ApartmentAvailabilityException(LocalDate start, LocalDate end) {
        super("Start date: " + start + " of availability is after end date: " + end);
    }
}
