package info.pionas.rental.query.apartment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Adi
 */
@RequiredArgsConstructor
//@Entity
@Table(name = "APARTMENT_BOOKING")
@Getter
public class ApartmentBookingReadModel {

    private final String bookintStep;
    private final LocalDateTime bookingDateTime;
    private final String ownerId;
    private final String tenantId;
    private final LocalDate periodStart;
    private final LocalDate periodEnd;
}
