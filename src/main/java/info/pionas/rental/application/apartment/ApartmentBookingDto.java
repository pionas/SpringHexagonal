package info.pionas.rental.application.apartment;

import info.pionas.rental.domain.apartment.NewApartmentBookingDto;
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

    public NewApartmentBookingDto asNewApartmentBookingDto() {
        return new NewApartmentBookingDto(getApartmentId(), getTenantId(), getStart(), getEnd());
    }
}
