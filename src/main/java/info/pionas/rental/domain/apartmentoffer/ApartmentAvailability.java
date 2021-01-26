package info.pionas.rental.domain.apartmentoffer;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class ApartmentAvailability {
    private final LocalDate start;
    private final LocalDate end;
}
