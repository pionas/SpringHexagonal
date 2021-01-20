package info.pionas.rental.infrastructure.rest.api.apartment;

import java.time.LocalDate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author Adi
 */
@RequiredArgsConstructor
@Getter
class ApartmentBookingDto {

    private final String tenentId;
    private final LocalDate start;
    private final LocalDate end;

}
