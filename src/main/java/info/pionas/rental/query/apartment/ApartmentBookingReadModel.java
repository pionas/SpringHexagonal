package info.pionas.rental.query.apartment;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Embeddable
public class ApartmentBookingReadModel {

    private String bookingStep;
    private LocalDateTime bookingDateTime;
    private String ownerId;
    private String tenantId;
    private LocalDate periodStart;
    private LocalDate periodEnd;
}
