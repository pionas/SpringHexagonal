package info.pionas.rental.domain.apartmentoffer;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
public class ApartmentAvailability {
    private final LocalDate start;
    private final LocalDate end;
}
