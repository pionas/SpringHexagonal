package info.pionas.rental.domain.apartmentbookinghistory;

import info.pionas.rental.domain.period.Period;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Embeddable
@SuppressWarnings("PMD.UnusedPrivateField")
public class ApartmentBooking {
    @Enumerated(EnumType.STRING)
    private BookingStep bookingStep;
    private LocalDateTime bookingDateTime;
    private String ownerId;
    private String tenantId;
    @Embedded
    private Period period;

    static ApartmentBooking start(LocalDateTime bookingDateTime, String ownerId, String tenantId, Period period) {
        return new ApartmentBooking(BookingStep.START, bookingDateTime, ownerId, tenantId, period);
    }
}
