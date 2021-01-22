package info.pionas.rental.domain.apartmentbookinghistory;

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
    private BookingPeriod bookingPeriod;

    public static ApartmentBooking start(LocalDateTime bookingDateTime, String ownerId, String tenantId, BookingPeriod bookingPeriod) {
        return new ApartmentBooking(BookingStep.START, bookingDateTime, ownerId, tenantId, bookingPeriod);
    }
}
