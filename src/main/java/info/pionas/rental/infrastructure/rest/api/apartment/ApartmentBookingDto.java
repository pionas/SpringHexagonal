package info.pionas.rental.infrastructure.rest.api.apartment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

/**
 * @author Adi
 */
@RequiredArgsConstructor
@Getter
class ApartmentBookingDto {

    private final String tenentId;
    private final LocalDate start;
    private final LocalDate end;

}
