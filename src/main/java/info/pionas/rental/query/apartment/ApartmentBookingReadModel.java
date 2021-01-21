package info.pionas.rental.query.apartment;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Adi
 */
@AllArgsConstructor(access = AccessLevel.PACKAGE)
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
