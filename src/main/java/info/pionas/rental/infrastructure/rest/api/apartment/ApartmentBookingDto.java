package info.pionas.rental.infrastructure.rest.api.apartment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;


@RequiredArgsConstructor
@Getter
public class ApartmentBookingDto {

    private final String tenantId;
    private final LocalDate start;
    private final LocalDate end;

}
