package info.pionas.rental.domain.apartmentbookinghistory;

import lombok.RequiredArgsConstructor;

import javax.persistence.Embedded;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * @author Adi
 */
@RequiredArgsConstructor
//@Entity
@Table(name = "APARTMENT_BOOKING")
public class ApartmentBooking {

    private final BookingStep bookintStep;
    private final LocalDateTime bookingDateTime;
    private final String ownerId;
    private final String tenantId;
    @Embedded
    private final BookingPeriod bookingPeriod;

    public static ApartmentBooking start(LocalDateTime bookingDateTime, String ownerId, String tenantId, BookingPeriod bookingPeriod) {
        return new ApartmentBooking(BookingStep.START, bookingDateTime, ownerId, tenantId, bookingPeriod);
    }
}
