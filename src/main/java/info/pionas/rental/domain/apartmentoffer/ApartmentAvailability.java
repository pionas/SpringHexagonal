package info.pionas.rental.domain.apartmentoffer;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
class ApartmentAvailability {
    private LocalDate start;
    private LocalDate end;

    public static ApartmentAvailability of(LocalDate start, LocalDate end) {
        if (start.isAfter(end)) {
            throw new ApartmentAvailabilityException(start, end);
        }
        return new ApartmentAvailability(start, end);
    }
}
