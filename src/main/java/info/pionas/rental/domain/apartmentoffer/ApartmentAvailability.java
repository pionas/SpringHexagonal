package info.pionas.rental.domain.apartmentoffer;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class ApartmentAvailability {
    private final LocalDate start;
    private final LocalDate end;

    public static ApartmentAvailability of(LocalDate start, LocalDate end) {
        if (start.isAfter(end)) {
            throw new ApartmentAvailabilityException();
        }
        return new ApartmentAvailability(start, end);
    }
}
