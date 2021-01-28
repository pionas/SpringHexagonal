package info.pionas.rental.application.apartment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
@Getter
public class ApartmentBookingDto {
    private final String apartmentId;
    private final String tenantId;
    private final LocalDate start;
    private final LocalDate end;
}
